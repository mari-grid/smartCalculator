package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    HashMap<String, BigInteger> var = new HashMap<>();

    @Test
    void assign_whenStoringNumberInVariable() {
        new Assignment().assign(var, "n = 5");
        BigInteger actualResult = var.get("n");
        BigInteger expectedResult = new BigInteger("5");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void assign_whenStoringVariableValueInVariable() {
        new Assignment().assign(var, "n = 500");
        new Assignment().assign(var, "m = n");
        BigInteger actualResult = var.get("m");
        BigInteger expectedResult = new BigInteger("500");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void assign_whenStoringNothingInVariable() {
        new Assignment().assign(var, "n =");
        String expectedResult = "Invalid assignment";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void assign_whenStoringNumberInNumber() {
        new Assignment().assign(var, "1 = 100");
        String expectedResult = "Invalid identifier";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void assign_whenStoringIncorrectVariableValueInVariable() {
        new Assignment().assign(var, "n = m");
        String expectedResult = "Unknown variable";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void assign_whenStoringValueWithUnknownCharactersInVariable() {
        new Assignment().assign(var, "n = 1$2");
        String expectedResult = "Invalid assignment";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
}