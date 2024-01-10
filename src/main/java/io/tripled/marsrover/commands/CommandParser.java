package io.tripled.marsrover.commands;

import io.tripled.marsrover.commands.*;

public class CommandParser {
    public static Command parseInput(String input) {
        if (input.isBlank() || input.equalsIgnoreCase("p")) {
            return PrintCommand.INSTANCE;
        } else if (input.equalsIgnoreCase("q")) {
            return QuitCommand.INSTANCE;
        }
        return new UnknownCommand(input);
    }

    public static Command createSimWorld(String maxCoordinate) {
        return new SimSetupCommand(maxCoordinate);
    }
}
