package com.example.productcatalogservice_nov2024.TableInheritanceExamples.MappedSuperclass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "msc_instructor")
public class Instructor extends User {
    private String company;
}
