package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void checkCommand_whenExit() {
        new Command().checkCommand("/exit");
        String expectedResult = "Bye!";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void checkCommand_whenHelp() {
        new Command().checkCommand("/help");
        String expectedResult = "The program calculates the addition, subtraction,"
                + " integer multiplication, and integer division of numbers";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
    @Test
    void checkCommand_whenUnknownCommand() {
        new Command().checkCommand("Unknown command");
        String expectedResult = "Unknown command";
        assertEquals(expectedResult, outputStreamCaptor.toString()
                .trim());
    }
}