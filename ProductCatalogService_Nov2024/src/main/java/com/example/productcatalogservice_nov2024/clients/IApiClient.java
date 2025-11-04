package com.example.productcatalogservice_nov2024.clients;

import com.example.productcatalogservice_nov2024.dtos.FakeStoreProductDto;
import com.example.productcatalogservice_nov2024.dtos.ProductDto;
import com.example.productcatalogservice_nov2024.models.Product;

import java.util.List;

public interface IApiClient {

    // HERE ONLY FAKE_STORE_PRODUCT_DTO COZ ITS FOR CLIENT
    FakeStoreProductDto[] getProducts();

    public FakeStoreProductDto getProductById(Long productId) ;

    FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto);

    FakeStoreProductDto replaceProduct(Long productId, FakeStoreProductDto inputFakeStoreProductDto);

    FakeStoreProductDto deleteProduct(Long productId);
}
