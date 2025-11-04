package com.example.productcatalogservice_nov2024.services;

import com.example.productcatalogservice_nov2024.config.RestTemplateConfig;
import com.example.productcatalogservice_nov2024.dtos.UserDto;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service("sps")
@Primary
public class StorageProductService implements IProductService{

    //THIS WILL GET THE DATA FROM DB THROUGH REPO LAYER

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private RestTemplateConfig restTemplate;

    @Override
    public List<Product> getProducts() {
        return List.of();
    }

    @Override
    public Product getProductById(Long productId) {
       Optional<Product> productOptional = productRepo.findById(productId);
       if(productOptional.isPresent()){
           return productOptional.get();
       }
       return null;
    }

    @Override
    public Product createProduct(Product inputProduct) {
        Optional<Product> productOptional = productRepo.createProduct(inputProduct);
        if(productOptional.isPresent()){
            return productOptional.get();
        }
        return null;
    }

    @Override
    public Product replaceProduct(Long productId, Product inputProduct) {
        return null;
    }

    @Override
    public Product deleteProduct(Long productId) {
        return null;
    }

    @Override
    public Product getProductBasedOnUserScope(Long productId, Long userId) {
        Optional<Product> productOptional = productRepo.findById(productId);
        if(productOptional.isEmpty()){
            return null;
        }
//        RestTemplate restTemplate = new RestTemplate(); //TO MAKE REST TEMPLATE LOAD BALANCED
        //ADD THE REST TEMPLATE AS A DEPENDENCY
//        UserDto userDto = restTemplate.getForEntity("http://localhost:9000/users/{userId}",UserDto.class ,userId).getBody();
//        INSTED OF USING ABOVE LOCAL HOST
//        USE NAME IN SERVICE DISCOVERY FOR AUTOMATED LOAD BALANCING
        UserDto userDto = restTemplate.restTemplate().getForEntity("http://userservice/users/{userId}",UserDto.class ,userId).getBody();
        //Optional<User> optionalUser = user
        if(userDto != null){
            System.out.println(userDto.getEmail());
            return productOptional.get();
        }
        return null;
    }
}
