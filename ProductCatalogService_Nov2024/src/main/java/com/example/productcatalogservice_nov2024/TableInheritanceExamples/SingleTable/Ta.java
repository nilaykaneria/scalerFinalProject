package com.example.productcatalogservice_nov2024.TableInheritanceExamples.SingleTable;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "st_ta")
@DiscriminatorValue(value = "1") //SINCE WE DISCRIMINATE ROWS FOR FASTER RETRIVAL FROM THE SINGLE TABLE WE NEED TO DISCRIMINATE WHAT KIND OF USER IS IT
public class Ta extends User {
    private Long hours;
}
