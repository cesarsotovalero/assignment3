package fr.inria.testproject;

import static org.junit.Assert.*;
import classes.Calculator;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void instantiate() throws Exception {
        calculator = new Calculator();
    }

    @Test
    public void add() {
        double result = calculator.add(10, 50);
        assertEquals(60, result, 0);
    }

    @Test
    public void substract() {
        double result = calculator.substract(10, 2);
        assertEquals(8, result, 0);
    }

    @Test
    public void multiply() {
        double result = calculator.multiply(10, 50);
        assertEquals(500, result, 0);
    }

//    @Test
//    public void divide() {
//        double result = calculator.divide(10, 2);
//        assertEquals(5, result, 0);
//    }
}
