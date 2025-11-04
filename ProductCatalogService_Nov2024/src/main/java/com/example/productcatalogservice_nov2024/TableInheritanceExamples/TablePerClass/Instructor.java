package com.example.productcatalogservice_nov2024.TableInheritanceExamples.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_instructor")
public class Instructor extends User{
    private String company;
}
