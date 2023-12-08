package calculator;

public class Command {

    String checkCommand(String val) {
        if ("/exit".equals(val)) {
            System.out.println("Bye!");
            return val;
        } else if ("/help".equals(val)) {
            System.out.println("The program calculates the addition, subtraction,"
                    + " integer multiplication, and integer division of numbers");
            return "/help";
        } else {
            System.out.println("Unknown command");
            return "Unknown command";
        }
    }
}
