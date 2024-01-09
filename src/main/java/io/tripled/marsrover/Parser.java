package io.tripled.marsrover;

import java.util.Objects;

public class Parser {
    public static Command parseInput(String input) {
        if (input.isEmpty() || input.equalsIgnoreCase("p")) {
            return PrintCommand.INSTANCE;
        }
        return new UnknownCommand(input);
    }
}
