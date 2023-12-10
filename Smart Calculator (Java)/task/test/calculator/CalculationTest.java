package calculator;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CalculationTest {
    HashMap<String, BigInteger> var = new HashMap<>();

    @Test
    void makeStringCorrect_whenItHasManySpaces() {
        BigInteger actualResult = new Calculation().calculate(var, "1   +       2");
        BigInteger expectedResult = new BigInteger("3");
        assertEquals(expectedResult, actualResult);
    }
    @Test
    void calculate() {
    }
}