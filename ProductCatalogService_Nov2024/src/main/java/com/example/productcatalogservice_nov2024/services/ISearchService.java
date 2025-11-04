package com.example.productcatalogservice_nov2024.services;

import com.example.productcatalogservice_nov2024.dtos.SortParam;
import com.example.productcatalogservice_nov2024.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ISearchService {

    Page<Product> search(String query, Integer pageNumber, Integer pageSize,List<SortParam> sortParams);

    //PAGE is DS IN the JpaRepository -> ListPagingAndSortingRepository -> Page extends Slice<T>
    //Sort is also class to sort the data and get it from DB used in Page
    //By default Page Number starts with 0
}
