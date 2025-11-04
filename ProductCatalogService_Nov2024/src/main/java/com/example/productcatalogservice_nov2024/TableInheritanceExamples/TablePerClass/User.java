package com.example.productcatalogservice_nov2024.TableInheritanceExamples.TablePerClass;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //Different types of Inheritance in DB
@Entity(name = "tpc_user")
public class User {
    @Id // @id to make it primary key
    private Long id;
    private String email;
}
