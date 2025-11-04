package com.example.productcatalogservice_nov2024;

public class Calculator {

    public Calculator() {
    }

    public int add(int a, int b){ return a+b;}
    public int subtract(int a, int b){ return a-b;}
    public int multiply(int a, int b){ return a*b;}
    public int divide(int a, int b){
        try {
            return a/b;
        }
        catch(ArithmeticException e){
            throw e;
        }
    }
}
