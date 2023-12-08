package calculator;

import java.lang.module.FindException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Scanner;

import static calculator.Calculation.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here

        String command = "anyWord";
        HashMap<String, BigInteger> var = new HashMap<>();

        while (!"/exit".equals(command)) {
            String val = scanner.nextLine();
            if (!val.isEmpty()) {
                if (val.matches("\\/[a-z]+")) {
                    command = new Command().checkCommand(val);
                } else if (val.contains("=")) {
                    new Assignment().assign(var, val);
                } else {
                    new Calculation().calculate(var, val);
                }
            }
        }
    }
}
