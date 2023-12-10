package calculator;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Stack;

public class Calculation {

    static int prec(char ch)
    {
        switch (ch) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    static String makeStringCorrect(String val) {
        val = val.replaceAll("\\s+", "");
        val = val.replaceAll("\\++", "+");

        while (val.contains("---")) {
            val = val.replaceAll("---", "-");
        }
        if (val.contains("--")) {
            val = val.replaceAll("--", "+");
        }
        return val;
    }

    private BigInteger evaluatePostfix(HashMap<String, BigInteger> var, String exp)
    {
        // Create a stack
        Stack<BigInteger> stack = new Stack<>();

        // Scan all characters one by one
        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            if (c == ' ')
                continue;

                // If the scanned character is an operand
                // (number here),extract the number
                // Push it to the stack.
            else if (Character.isDigit(c)) {
                BigInteger n = BigInteger.ZERO;

                // Extract the characters and store it in num
                while (Character.isDigit(c)) {
                    n = n.multiply(BigInteger.valueOf(10))
                            .add(BigInteger.valueOf(Long.parseLong(String.valueOf(c))));
                    i++;
                    c = exp.charAt(i);
                }
                i--;

                // Push the number in stack
                stack.push(n);
            }
            else if (Character.isAlphabetic(c)) {
                StringBuilder s = new StringBuilder();
                int n = i;
                // Extract the characters and store it in String
                while (Character.isAlphabetic(c)) {
                    s.append(c);
                    i++;
                    c = exp.charAt(i);
                }
                i = n;

                // Push the number in stack
                stack.push(getNumber(var, String.valueOf(s)));
            }

            // If the scanned character is an operator, pop
            // two elements from stack apply the operator
            else {
                BigInteger val1 = stack.pop();
                BigInteger val2 = BigInteger.ZERO;
                if (!stack.isEmpty()) {
                    val2 = stack.pop();
                }

                switch (c) {
                    case '+':
                        stack.push(val2.add(val1));
                        break;
                    case '-':
                        stack.push(val2.subtract(val1));
                        break;
                    case '/':
                        stack.push(val2.divide(val1));
                        break;
                    case '*':
                        stack.push(val2.multiply(val1));
                        break;
                    /*case '^':
                        stack.push(val2.pow(val1));
                        break;*/
                }
            }
        }
        return stack.pop();
    }
    private BigInteger getNumber(HashMap<String, BigInteger> var, String str) {
        if (str.matches("[a-zA-Z]+")) {
            if (var.containsKey(str)){
                return var.get(str);
            } else {
                System.out.println("Unknown variable");
                return null;
            }
        } else if (str.matches("\\d+")) {
            return new BigInteger(str);
        } else {
            System.out.println("Invalid identifier");
            return null;
        }
    }

    private String infixToPostfix(String exp) {
        // Initializing empty String for result
        String result = new String("");

        // Initializing empty stack
        Deque<Character> stack
                = new ArrayDeque<>();

        for (int i = 0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            // If the scanned character is an
            // operand, add it to output.
            if (Character.isLetterOrDigit(c)){
                result += c;
                if (i < exp.length() - 1) {
                    if (!Character.isLetterOrDigit(exp.charAt(i + 1))) {
                        result += " ";
                    }
                }
            }


                // If the scanned character is an '(',
                // push it to the stack.
            else if (c == '(')
                stack.push(c);

                // If the scanned character is an ')',
                // pop and output from the stack
                // until an '(' is encountered.
            else if (c == ')') {
                while (!stack.isEmpty()
                        && stack.peek() != '(') {
                    result += stack.peek() + " ";
                    stack.pop();
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                } else {
                    System.out.println("Invalid expression");
                    return "Invalid expression";
                }
            }

            // An operator is encountered
            else
            {
                while (!stack.isEmpty()
                        && prec(c) <= prec(stack.peek())) {

                    result += stack.peek() + " ";
                    stack.pop();
                }
                stack.push(c);
            }
        }

        // Pop all the operators from the stack
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                System.out.println("Invalid expression");
                return "Invalid expression";
            }
            result += stack.peek() + " ";
            stack.pop();
        }

        return result;
    }

    BigInteger calculate(HashMap<String, BigInteger> var, String val) {
        val = val.trim();
        if (val.matches("\\d+")) {
            System.out.println(val);
            return new BigInteger(val);
        } else if (val.matches("[a-zA-Z]+")) {
            System.out.println(getNumber(var, val));
            return new BigInteger(String.valueOf(getNumber(var, val)));
        }else {
            val = makeStringCorrect(val);
            if (val.length() == 0 || val.contains("**") || val.contains("//")) {
                System.out.println("Invalid expression");
                return null;
            } else {
                String res = infixToPostfix(val);
                //System.out.println(res);
                if (!"Invalid expression".equals(res)){
                    System.out.println(evaluatePostfix(var, res));
                    return new BigInteger(String.valueOf(evaluatePostfix(var, res)));
                } else {
                    return null;
                }
            }
        }
    }
}
