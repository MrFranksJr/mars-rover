package io.tripled.marsrover;

import java.util.Objects;

public class Parser {
    public static Command parseInput(String input) {
        if (input.isBlank() || input.equalsIgnoreCase("p")) {
            return PrintCommand.INSTANCE;
        } else if (input.equalsIgnoreCase("q")) {
            return QuitCommand.INSTANCE;
        }
        return new UnknownCommand(input);
    }
}
