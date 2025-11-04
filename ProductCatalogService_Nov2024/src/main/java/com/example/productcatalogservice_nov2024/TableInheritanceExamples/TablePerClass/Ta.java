package com.example.productcatalogservice_nov2024.TableInheritanceExamples.TablePerClass;

import jakarta.persistence.Entity;

@Entity(name = "tpc_ta")
public class Ta extends User{
    private Long hours;
}
