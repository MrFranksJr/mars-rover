package io.tripled.marsrover.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CommandParserTest {
    @Test
    void parseAnything() {
        Command brol = CommandParser.parseInput("brol");

        UnknownCommand expectedCommand = new UnknownCommand("brol");

        Assertions.assertEquals(brol, expectedCommand);
    }
    @Test
    void parsePrint() {
        Command print = CommandParser.parseInput("p");

        Assertions.assertEquals(print, PrintCommand.INSTANCE);
    }
    @Test
    void parseEmptyCommand() {
        Command emptyString = CommandParser.parseInput("");

        Assertions.assertEquals(emptyString, PrintCommand.INSTANCE);
    }
    @Test
    void parseSpaceCommand() {
        Command spaceString = CommandParser.parseInput(" ");

        Assertions.assertEquals(spaceString, PrintCommand.INSTANCE);
    }
    @Test
    void parseQuitCommand() {
        Command quit = CommandParser.parseInput("q");

        Assertions.assertEquals(quit, QuitCommand.INSTANCE);
    }
    @Test
    void parseCoordinate() {
        Command coordinate = CommandParser.parseInput("52");

        SimSetupCommand expectedCommand = new SimSetupCommand("52");

        Assertions.assertEquals(coordinate, expectedCommand);
    }
    @Test
    void parseInvalidCoordinate() {
        Command coordinate = CommandParser.parseInput("202");

        SimSetupCommand expectedCommand = new SimSetupCommand("202");

        Assertions.assertEquals(coordinate, expectedCommand);
    }

}