package com.example.productcatalogservice_nov2024.services;

import com.example.productcatalogservice_nov2024.dtos.SortParam;
import com.example.productcatalogservice_nov2024.dtos.SortType;
import com.example.productcatalogservice_nov2024.models.Product;
import com.example.productcatalogservice_nov2024.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageSearchService implements ISearchService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Page<Product> search(String query, Integer pageNumber, Integer pageSize, List<SortParam> sortParams) {
//        Sort sort =  Sort.by("price")//To sort the data
//                .and(Sort.by("id").descending()); //When you need to further sort if price is equal
        Sort sort = null;
        if(!sortParams.isEmpty()) {
            if(sortParams.get(0).getSortType().equals(SortType.ASCENDING)){
                sort = sort.by(sortParams.get(0).getSortCriteria());
            }
            else{
                sort = sort.by(sortParams.get(0).getSortCriteria()).descending();
            }
        }

        for(int i = 1; i < sortParams.size(); i++) {
            if(sortParams.get(i).getSortType().equals(SortType.ASCENDING)) {
                sort = sort.and(Sort.by(sortParams.get(i).getSortCriteria()));
            }else {
                sort = sort.and(Sort.by(sortParams.get(i).getSortCriteria()).descending());
            }
        }


        if(sort != null){
            return productRepo.findByName(query, PageRequest.of(pageNumber,pageSize,sort));
        }
        else{
            return productRepo.findByName(query, PageRequest.of(pageNumber,pageSize));
        }
    }

}
