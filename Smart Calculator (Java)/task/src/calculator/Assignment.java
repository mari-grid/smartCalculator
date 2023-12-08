package calculator;

import java.math.BigInteger;
import java.util.HashMap;

public class Assignment {

    HashMap<String, BigInteger> assign(HashMap<String, BigInteger> var, String val) {
        String[] arr = val.split("=");
        if (arr.length < 3) {
            if (arr[0].trim().matches("[a-zA-Z]+")) {
                if (arr[1].trim().matches("\\d+")) {
                    var.put(arr[0].trim(), new BigInteger(arr[1].trim()));
                } else if (arr[1].trim().matches("[a-zA-Z]+")) {
                    if (var.containsKey(arr[1].trim())){
                        var.put(arr[0].trim(), var.get(arr[1].trim()));
                    } else {
                        System.out.println("Unknown variable");
                    }
                } else {
                    System.out.println("Invalid assignment");
                }
            } else {
                System.out.println("Invalid identifier");
            }
        } else {
            System.out.println("Invalid assignment");
        }
        return var;
    }
}
