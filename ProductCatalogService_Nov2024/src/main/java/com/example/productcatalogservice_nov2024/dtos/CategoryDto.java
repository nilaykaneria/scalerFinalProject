package com.example.productcatalogservice_nov2024.dtos;

import com.example.productcatalogservice_nov2024.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
