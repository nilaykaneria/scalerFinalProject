package com.example.userauthenticationservice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class SpringSecurity {

    //BECAUSE THE SAME FUNCTION NAME PRESENT IN THE SECURITY CONFIG
    //BTW SECURITY CONFIG FILE IS TO IMPLEMENT OAUTH (ALL THIS BELOW WAS TO IMPLEMENT AUTH WITH JWT)
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
//        //BASICALLY TO DISABLE SPRING SECURITY STARTER PAGE (THAT COMES IN BETWEEN API CALLS)
//        // !! (COMING FROM THE DEPENDENCY THAT WE ADDED, SIDE EFFECT OF ADDING DEPENDENCY)
//        //COZ OF SOME CODE //SO WE OVERRIDE THAT CODE
//        httpSecurity.cors().disable();
//        httpSecurity.csrf().disable();
//        httpSecurity.authorizeHttpRequests(authoriz-> authoriz.anyRequest().permitAll());
//        return httpSecurity.build();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean //Singelton
    public SecretKey secretKey(){
        MacAlgorithm algorithm = Jwts.SIG.HS256; // FOR THE HEADER (ENCRYPTION TITLE NAME)
        SecretKey secretKey = algorithm.key().build();
        return secretKey;
    }

}
