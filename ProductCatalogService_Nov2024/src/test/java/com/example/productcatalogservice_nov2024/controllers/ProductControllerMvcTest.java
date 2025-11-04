package com.example.productcatalogservice_nov2024.controllers;

import com.example.productcatalogservice_nov2024.dtos.ProductDto;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.services.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
@WebMvcTest(ProductController.class)
public class ProductControllerMvcTest {

    //HERE WE ARE NOT ADDING PRODUCT CONTROLLER AS DEPENDENCY //JUST
    //DISPACHER SERVLET WILL HELP IN FORWARDING THE REQUEST PRODUCT CONTROLLER
    // BASED ON CLIENT
    //REQUEST TYPE ,(MAYBE GOOD REQUEST OR BAD REQUEST)

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IProductService productService;

    @Test
    public void TestGetAllProducts_RunSuccessfully() throws Exception {
        mockMvc.perform(get("/products")).andExpect(status().isOk());

    }

//    @Test
//    public void TestGetAllProducts_ReceivesProductList() throws Exception {
//        //Arrange
//        Product product = new Product();
//        product.setId(1L);
//        product.setName("Product 1");
//        product.setDescription("Product 1");
//        Product product2 = new Product();
//        product2.setId(2L);
//        product2.setName("Product 2");
//        product2.setDescription("Product 2");
//        List<Product> productList = new ArrayList<>();
//        productList.add(product);
//        productList.add(product2);
//
//        when(productService.getProducts()).thenReturn(productList);
//
//        mockMvc.perform(get("/products"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(productList)));
//
//    }

    @Test
    public void TestCreateProductApi_RunsSuccessfully() throws Exception {
        //Arrange
        Product product = new Product();
        product.setId(1L);
        product.setName("Nilay");
        product.setDescription("Product 1");

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Nilay");
        productDto.setDescription("Product 1");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        //ACT and ASSERT

//        mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(productDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));

        //CONCEPTUALLY ABOVE SHOULD WORK //THIS CODE MAKES SENSE

        //NOT WORKING BECAUSE OF CONTENT TYPE (WHICH POST MAN HANDELS FOR US ) HERE WE NEED TO MANUALLY HANDEL IT
        mockMvc.perform(post("/products").content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));
    }

}
