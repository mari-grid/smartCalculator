package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    HashMap<String, BigInteger> var = new HashMap<>();

    @Test
    void makeStringCorrect_whenStringHasManySpacesNearOperators() {
        BigInteger actualResult = new Calculation().calculate(var, "1   +       2");
        BigInteger expectedResult = new BigInteger("3");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void makeStringCorrect_whenStringHasManyPluses() {
        BigInteger actualResult = new Calculation().calculate(var, "1 +++++ 2");
        BigInteger expectedResult = new BigInteger("3");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void makeStringCorrect_whenStringHasOddNumberOfMinuses() {
        BigInteger actualResult = new Calculation().calculate(var, "2 --- 1");
        BigInteger expectedResult = new BigInteger("1");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void makeStringCorrect_whenStringHasEvenNumberOfMinuses() {
        BigInteger actualResult = new Calculation().calculate(var, "1 ---- 2");
        BigInteger expectedResult = new BigInteger("3");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenSpacesBeforeExpression() {
        BigInteger actualResult = new Calculation().calculate(var, "   1 + 2");
        BigInteger expectedResult = new BigInteger("3");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenSpacesAfterExpression() {
        BigInteger actualResult = new Calculation().calculate(var, "1 + 2     ");
        BigInteger expectedResult = new BigInteger("3");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenInputIsOnlyNumber() {
        BigInteger actualResult = new Calculation().calculate(var, "123");
        BigInteger expectedResult = new BigInteger("123");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenInputIsOnlyExistingVar() {
        new Assignment().assign(var, "variable = 123");
        BigInteger actualResult = new Calculation().calculate(var, "variable");
        BigInteger expectedResult = new BigInteger("123");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenInputIsEmpty() {
        new Calculation().calculate(var, "");
        String expectedResult = "Invalid expression";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void calculate_whenInputContainsDoubleMultiplyOperator() {
        new Calculation().calculate(var, "2 ** 3");
        String expectedResult = "Invalid expression";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void calculate_whenInputContainsDoubleDivisionOperator() {
        new Calculation().calculate(var, "2 // 3");
        String expectedResult = "Invalid expression";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void calculate_whenAddingNumbers() {
        BigInteger actualResult = new Calculation().calculate(var, "2 + 3");
        BigInteger expectedResult = new BigInteger("5");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenSubtractingNumbers() {
        BigInteger actualResult = new Calculation().calculate(var, "100 - 1");
        BigInteger expectedResult = new BigInteger("99");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenDividingNumbers() {
        BigInteger actualResult = new Calculation().calculate(var, "100 / 10");
        BigInteger expectedResult = new BigInteger("10");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenMultiplyingNumbers() {
        BigInteger actualResult = new Calculation().calculate(var, "100 * 10");
        BigInteger expectedResult = new BigInteger("1000");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenAddingVariables() {
        new Assignment().assign(var, "n = 111");
        new Assignment().assign(var, "m = 222");
        BigInteger actualResult = new Calculation().calculate(var, "n + m");
        BigInteger expectedResult = new BigInteger("333");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenSubtractingVariables() {
        new Assignment().assign(var, "n = 333");
        new Assignment().assign(var, "m = 222");
        BigInteger actualResult = new Calculation().calculate(var, "n - m");
        BigInteger expectedResult = new BigInteger("111");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenDividingVariables() {
        new Assignment().assign(var, "n = 333");
        new Assignment().assign(var, "m = 3");
        BigInteger actualResult = new Calculation().calculate(var, "n / m");
        BigInteger expectedResult = new BigInteger("111");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenMultiplyingVariables() {
        new Assignment().assign(var, "n = 111");
        new Assignment().assign(var, "m = 3");
        BigInteger actualResult = new Calculation().calculate(var, "n * m");
        BigInteger expectedResult = new BigInteger("333");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenMultiplyingAtEnd() {
        BigInteger actualResult = new Calculation().calculate(var, "2 + 3 * 4");
        BigInteger expectedResult = new BigInteger("14");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenDividingAtEnd() {
        BigInteger actualResult = new Calculation().calculate(var, "6 + 4 / 2");
        BigInteger expectedResult = new BigInteger("8");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenInputWithBrackets() {
        BigInteger actualResult = new Calculation().calculate(var, "(6 + 4) / 2");
        BigInteger expectedResult = new BigInteger("5");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenOnlyOpenBracket() {
        new Calculation().calculate(var, "(1 + 2 * 3");
        String expectedResult = "Invalid expression";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void calculate_whenUnknownVariable() {
        new Calculation().calculate(var, "n");
        String expectedResult = "Unknown variable";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void calculate_whenUnknownCharacters() {
        new Calculation().calculate(var, "1#$%26");
        String expectedResult = "Invalid identifier";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void calculate_whenBigIntegers() {
        BigInteger actualResult = new Calculation().calculate(var,
                "112234567890 + 112234567890 * (10000000999 - 999)");
        BigInteger expectedResult = new BigInteger("1122345679012234567890");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate_whenAssignmentWithBigIntegers() {
        new Assignment().assign(var, "n = 800000000000000000000000");
        new Assignment().assign(var, "m = 100000000000000000000000");
        BigInteger actualResult = new Calculation().calculate(var, "n + m");
        BigInteger expectedResult = new BigInteger("900000000000000000000000");
        assertEquals(expectedResult, actualResult);
    }
}