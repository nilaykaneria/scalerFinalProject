package com.example.productcatalogservice_nov2024.models;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//GOT This because of Lombok Dependency
@Setter
@Getter
@MappedSuperclass //need all this fields as columns in the child class table and not to create a table of this class
public abstract class BaseModel {
    @Id
    private Long id;
    private Date createdAt;
    private Date lastUpdatedAt;
    private State sate;
}
