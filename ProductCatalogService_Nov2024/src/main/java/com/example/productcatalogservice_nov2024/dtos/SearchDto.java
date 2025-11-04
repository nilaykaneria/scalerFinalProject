package com.example.productcatalogservice_nov2024.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class SearchDto {
    private String query;
    private Integer pageSize;
    private Integer pageNumber;
    private List<SortParam> sortParams = new ArrayList<>();
}
