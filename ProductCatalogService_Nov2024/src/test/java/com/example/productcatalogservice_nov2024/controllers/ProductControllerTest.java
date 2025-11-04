package com.example.productcatalogservice_nov2024.controllers;

import com.example.productcatalogservice_nov2024.dtos.ProductDto;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.services.IProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
//    @Qualifier("sps")
    private IProductService productService;

    //AS FAR AS I UNDERSTAND MOCK IS USED TO CREATE A SCENARIO
    //THAT RETURNS A MOCK RESPONSE WHEN WE MAKE A API CALL

//    @Test
//    public void TestGetProductById_WithValidId_RunSuccessfully() {
//        //arrange
//        Long productId = 2L;
//        Product  product = new Product();
//        product.setId(productId);
//        product.setName("Iphone");
//        when(productService.getProductById(productId))
//                .thenReturn(product);
//
//        //BASICALLY WHEN THE ABOVE RUNS RETURN THAT PRODUCT AS OUTPUT AND THEN COMEPARE THE OUTPUT
//        //act
//        ResponseEntity<ProductDto> productDtoResponseEntity
//                = productController.getProductById(productId);
//
//        //assert
//        assertNotNull(productDtoResponseEntity);
//        assertNotNull(productDtoResponseEntity.getBody());
//        assertEquals(productId, productDtoResponseEntity.getBody().getId());
//        assertEquals("Iphone", productDtoResponseEntity.getBody().getName());
//
//        //WANT TO VERIFY HOW MANY TIMES THE FUNCTION IS INVOKED/ CALLED //BASICALLY MOCK VERIFY
//        verify(productService,times(2)).getProductById(productId);
//    }

    @Test
    public void TestGetProductById_WithZeroId_ThrowsIllegalArgsException() {
        //act and assert
        assertThrows(IllegalArgumentException.class,
                ()-> productController.getProductById(0L));
    }

    //BASICALLY IF A FUNCTION RETURNS SAME EXCEPTION FROM 2 DIFFERENT PLACES THIS
    //IS HOW WE CAN DO THE TEST CASES
    @Test
    public void TestGetProductById_WithNegativeId_ThrowsIllegalArgsExceptionWithCheckMessage() {
        //act and assert
        Exception exception = assertThrows(IllegalArgumentException.class,
                ()-> productController.getProductById(-1L));

        assertEquals("Product Id cannot be negative.", exception.getMessage());
    }

    // Considering a scenario where exception is getting thrown from downStream
    @Test
    public void getProductById_WhenProductServiceThrowsException_ThrowsSameException() {
        when(productService.getProductById(anyLong()))
                .thenThrow(new RuntimeException("something went bad!!"));

        assertThrows(RuntimeException.class, () -> productController.getProductById(2L));
    }
}