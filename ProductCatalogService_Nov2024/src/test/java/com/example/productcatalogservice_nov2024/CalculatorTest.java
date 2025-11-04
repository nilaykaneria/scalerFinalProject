package com.example.productcatalogservice_nov2024;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test //Just add this to get the play button at the side
//    methodName_when_then
    public void AdditionOn2Integers_RunSuccessfully() {
        //arrange
        Calculator calculator = new Calculator();

        //act
        int res = calculator.add(3,4);

        //assert
        assert(res == 7);

//        assertEquals(7,res); Another way to write the code
    }

    @Test
    public void DivisionOn2Integers_ThrowsException() {
        //arrange
        Calculator calculator = new Calculator();

        //act        //assert
        assertThrows(ArithmeticException.class,
                () -> calculator.divide(1,0));

    }
}