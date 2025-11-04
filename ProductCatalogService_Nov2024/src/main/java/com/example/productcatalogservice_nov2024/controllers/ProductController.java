package com.example.productcatalogservice_nov2024.controllers;

import com.example.productcatalogservice_nov2024.dtos.CategoryDto;
import com.example.productcatalogservice_nov2024.dtos.ProductDto;
import com.example.productcatalogservice_nov2024.models.Category;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.services.IProductService;
import com.example.productcatalogservice_nov2024.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // for creating beam(singleton) for this class
public class ProductController{

    //VALIDATIONS ARE IN THIS CLASS BECAUSE
    //WHEN WE ARE AT RESTAURANT WHEN WE ORDER CROW BIRYANI
    //CHEF WON'T COME RUNNING AND TELL US IT'S NOT AVAILABLE
    @Autowired
//    @Qualifier("sps") //which service to use 3rd party or Storage Product Service(sps)
    public IProductService productService;

    //Since Product model must not be visible(for safe business logic) to the end request we create a DTO
    //We Do not expose the model coz then he can make changes in the request(dangerous)
//    public List<Product> getProducts() { //just the method not an API
    @GetMapping("/products")//Now it's an API bcz of this annotation //root //path = /products
    public ResponseEntity<List<ProductDto>> getProducts(){
       List<Product> products = productService.getProducts();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        if (products == null) {
            headers.add("message", "No products found.");
            return new ResponseEntity<>(null, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<ProductDto> productDtoList = Utility.getProductDtoList(products);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    //Since we want to send custom Status code based on our requirement we Send Response Entity
    //Else everything will be handled by the Dispatcher Servlet
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        try {
            if(productId < 0){
                throw new IllegalArgumentException("Product Id cannot be negative.");
            } else if (productId == 0) {
                throw new IllegalArgumentException("Product Id cannot be accessible");
            }
            Product product = productService.getProductById(productId);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            if (product == null) {
                headers.add("message", "Product with product id " + productId + " not found.");
                return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
            }
            ProductDto productDto = Utility.getProductDto(product);
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
        catch (IllegalArgumentException exception){
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            //Since we are returning a response so the function "handelException" will not be called
            // so we just need to throw the exception
            throw exception;
        }
    }

    @PostMapping("/products")
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        Product inputProduct = Utility.getProduct(productDto);
        Product response = productService.createProduct(inputProduct);
        return Utility.getProductDto(response);
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable Long id, @RequestBody ProductDto productDto){
        Product inputProoduct = Utility.getProduct(productDto);
        Product response =  productService.replaceProduct(id, inputProoduct);
        return Utility.getProductDto(response);
    }

    @DeleteMapping("/products/{id}")
    public ProductDto deleteProduct(@PathVariable Long id){
        Product response = productService.deleteProduct(id);
        return Utility.getProductDto(response);
    }

    //FOR EUREKA

    @GetMapping("/products/{productId}/{userId}")
    public ProductDto getProductDetailsBasedOnUserScope(@PathVariable Long productId, @PathVariable Long userId){
        Product product = productService.getProductBasedOnUserScope(productId,userId);
        return Utility.getProductDto(product);
    }


}
