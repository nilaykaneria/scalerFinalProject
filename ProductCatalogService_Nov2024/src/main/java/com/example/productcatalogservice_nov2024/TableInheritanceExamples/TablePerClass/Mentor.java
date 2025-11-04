package com.example.productcatalogservice_nov2024.TableInheritanceExamples.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_mentor")
public class Mentor extends User{
    private Double ratings;
}
