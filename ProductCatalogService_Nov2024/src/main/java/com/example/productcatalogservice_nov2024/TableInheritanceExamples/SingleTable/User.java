package com.example.productcatalogservice_nov2024.TableInheritanceExamples.SingleTable;

import jakarta.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Different types of Inheritance in DB
@Entity(name = "st_user")
@DiscriminatorColumn(name = "user_type",discriminatorType = DiscriminatorType.INTEGER)//THIS IS BECAUSE we need to discriminate which kind of users (is it mentor or ta or instructor)
public class User {
    @Id // by default will take name as id if no name given in primarykeyjoinColumn
    private Long id;
    private String email;
}

//WHEN I SKIPPED ONE VALUE TO FILL PrimaryKeyJoinColumn THEN IT CREATED 3 COLUMNS ELSE ONLY 2 COLUMNS ARE THERE
