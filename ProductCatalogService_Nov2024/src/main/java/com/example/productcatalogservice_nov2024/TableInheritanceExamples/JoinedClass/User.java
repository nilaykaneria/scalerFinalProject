package com.example.productcatalogservice_nov2024.TableInheritanceExamples.JoinedClass;

import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED) //Different types of Inheritance in DB
@Entity(name = "jc_user")
public class User {
    @Id // by default will take name as id if no name given in primarykeyjoinColumn
    private Long id;
    private String email;
}

//WHEN I SKIPPED ONE VALUE TO FILL PrimaryKeyJoinColumn THEN IT CREATED 3 COLUMNS ELSE ONLY 2 COLUMNS ARE THERE
