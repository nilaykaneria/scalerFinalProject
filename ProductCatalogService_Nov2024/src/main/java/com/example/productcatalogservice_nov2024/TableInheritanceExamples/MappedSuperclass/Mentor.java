package com.example.productcatalogservice_nov2024.TableInheritanceExamples.MappedSuperclass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity(name = "msc_mentor")
public class Mentor extends User {
    private Double ratings;
}
