package io.tripled.marsrover.input;

import io.tripled.marsrover.commands.*;

public class InputParser {
    public static Command parseInput(String input) {
        if (input.isBlank() || input.equalsIgnoreCase("p")) {
            return PrintCommand.INSTANCE;
        } else if (input.equalsIgnoreCase("q")) {
            return QuitCommand.INSTANCE;
        } else if (input.matches("\\d+")) {
            return new SimSetupCommand(input);
        }
        return new UnknownCommand(input);
    }
}
