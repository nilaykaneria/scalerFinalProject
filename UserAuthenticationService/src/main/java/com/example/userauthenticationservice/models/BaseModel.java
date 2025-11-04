package com.example.userauthenticationservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //this is done to automatically manage the ID, only created when it is going to get stored in DB
    private Long id; // Not interested in taking Input from the user // just for our ref, hence the above


    private State status;
    private Date createdAt;
    private Date updatedAt;
}
