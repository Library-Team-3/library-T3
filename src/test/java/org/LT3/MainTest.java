package org.LT3;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainRunsWithoutExceptions() {
        String input = "0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        assertDoesNotThrow(() -> Main.main(new String[]{}));

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to the library!"));

        System.setOut(originalOut);
        System.setIn(System.in);
    }
}
