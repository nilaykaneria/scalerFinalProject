package com.example.productcatalogservice_nov2024.repos;


import com.example.productcatalogservice_nov2024.models.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepoTest {

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void addTestProductsInAwsDb(){
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Product Description");
        product.setPrice(1000.0);

        Product product2 = new Product();
        product.setId(2L);
        product.setName("Test Product 2");
        product.setDescription("Test Product 2 Description");
        product.setPrice(200.2);
    }

    @Test
    @Transactional
    public void testJpaMethod() {
//        List<Product> products = productRepo.findProductByPriceBetween(1000.5 ,10000.00);
        List<Product> products = productRepo.findProductByIsPrime(true);
        System.out.println(products.size() + " products found");
    }
}