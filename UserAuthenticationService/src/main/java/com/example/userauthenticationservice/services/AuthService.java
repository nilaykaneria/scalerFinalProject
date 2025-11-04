package com.example.userauthenticationservice.services;

import com.example.userauthenticationservice.client.KafkaProducerClient;
import com.example.userauthenticationservice.dtos.EmailDto;
import com.example.userauthenticationservice.exceptions.IncorrectPasswordException;
import com.example.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.example.userauthenticationservice.exceptions.UserDoesnotExistException;
import com.example.userauthenticationservice.models.State;
import com.example.userauthenticationservice.models.User;
import com.example.userauthenticationservice.models.UserSession;
import com.example.userauthenticationservice.repos.SessionRepo;
import com.example.userauthenticationservice.repos.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private UserRepo userRepo; //Talking to the DB we need a repo(DAO)

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SessionRepo sessionRepo;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private KafkaProducerClient kafkaProducerClient;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper(); //TO CONVERT TO STRING /JSON

    @Override
    public User signup(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists. Please try different email!");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepo.save(user);

        //AFTER SIGN UP DONE WE HAVE TO NOTIFY (SO WE SEND MESSAGE TO KAFKA) // WE WILL SEND ONLY MESSAGE
        try {
            EmailDto emailDto = new EmailDto();
            emailDto.setTo(email);
            emailDto.setFrom("nilaykaneria_@gmail.com");
            emailDto.setSubject("Welcome to Scaler !!");
            emailDto.setBody("Hope you have a great stay.");

            kafkaProducerClient.sendMessage("new_user", objectMapper.writeValueAsString(emailDto));
        }
        catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }

        return user;
    }

    @Override
    public Pair<User, MultiValueMap<String, String>> login(String email, String password) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserDoesnotExistException("User doesn't exists. Please try other email!");
        }

        if(!bCryptPasswordEncoder.matches(password,optionalUser.get().getPassword())){
//        if(!optionalUser.get().getPassword().equals(password)){
            throw new IncorrectPasswordException("Incorrect password!");
        }

        //NEED TO CREATE A JWT

        //Payload
//        String message = "The Payload of the jwt, actual info.";

        Map<String,Object> userClaims = new HashMap<>();
        userClaims.put("userId",optionalUser.get().getId());
        userClaims.put("permissions",optionalUser.get().getRoles());
        Long currentTimeInMillis = System.currentTimeMillis();
        userClaims.put("iat",currentTimeInMillis);
        userClaims.put("exp",currentTimeInMillis+864000);
        userClaims.put("issuer","scaler");





        //Header


//        byte[] content = message.getBytes(StandardCharsets.UTF_8);
//        String jwtToken = Jwts.builder().content(content).compact();
        String jwtToken = Jwts.builder().claims(userClaims).signWith(secretKey).compact(); //JUST ADDITION OF ONE WORD
        //JUST THE 2 LINES FOR JWT GENERATION

        //FOR SENDING THE JWT
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,jwtToken);

        //CREATE SESSION BEFORE SENDING
        //this is done for validation purpose
        UserSession userSession = new UserSession();
        userSession.setToken(jwtToken);
        userSession.setUser(optionalUser.get());
        userSession.setStatus(State.ACTIVE);
        sessionRepo.save(userSession);// CREATE A USER SESSION and SAVE in DB


        Pair<User,MultiValueMap<String, String>> response = new Pair<>(optionalUser.get(),headers);

        return response;
    }

    @Override
    public Boolean validateToken(Long userId,String token){
        Optional<UserSession> optionalUserSession = sessionRepo.findByTokenAndUser_Id(token,userId);

        if(optionalUserSession.isEmpty()){
            return false;
        }

        UserSession userSession = optionalUserSession.get();

        String persitedToken = userSession.getToken();
//        JwtParser jwtParser = Jwts.parser().build();
        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();

        Claims claims = jwtParser.parseSignedClaims(persitedToken).getBody();
        Long expiryStoredInToken = (Long)claims.get("exp");
        Long currentTime = System.currentTimeMillis();

        if(currentTime > expiryStoredInToken){
            userSession.setStatus(State.INACTIVE);
            sessionRepo.save(userSession); //SAVE IN DB
            //ALSO MAKE THE SESSION AS INACTIVE BEFORE RETURNING FALSE
            return false;
        }
        return true;
    }
}
