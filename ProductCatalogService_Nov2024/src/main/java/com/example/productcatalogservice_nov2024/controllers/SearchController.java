package com.example.productcatalogservice_nov2024.controllers;

import com.example.productcatalogservice_nov2024.dtos.SearchDto;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public Page<Product> searchProducts(@RequestBody SearchDto searchDto) throws Exception
    {
        Page<Product> productPage = searchService.search(
                searchDto.getQuery(),searchDto.getPageNumber(), searchDto.getPageSize(),searchDto.getSortParams());
        return productPage;//Utility.from(productList);

        //to do page of product dto from page of product
    }

}
