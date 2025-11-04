package com.example.productcatalogservice_nov2024.TableInheritanceExamples.MappedSuperclass;

import jakarta.persistence.*;

//THIS WILL WORK ONLY ON BASE CLASS (i.e ABSTRACT CLASS) //works without abstract word
@MappedSuperclass
public abstract class User {
    @Id
    private Long id;
    private String email;
}

