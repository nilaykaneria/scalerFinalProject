package com.example.productcatalogservice_nov2024.services;

import com.example.productcatalogservice_nov2024.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_nov2024.dtos.ProductDto;
import com.example.productcatalogservice_nov2024.models.Product;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IProductService {
    List<Product> getProducts();

    Product getProductById(Long productId);

    Product createProduct(Product inputProduct);

    Product replaceProduct(Long productId, Product inputProduct);

    Product deleteProduct(Long productId);

    Product getProductBasedOnUserScope( Long productId, Long userId);
}
