package com.example.productcatalogservice_nov2024.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class FakeStoreProductDto implements Serializable {
    Long id;
    String title;
    Double price;
    String description;
    String image;
    String category;
}
