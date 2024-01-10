package io.tripled.marsrover.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandParserTest {
    @Test
    void parseAnything() {
        Command brol = CommandParser.parseInput("brol");

        UnknownCommand expectedCommand = new UnknownCommand("brol");

        assertEquals(brol, expectedCommand);
    }

    @Test
    void parsePrint() {
        Command print = CommandParser.parseInput("p");

        assertEquals(print, PrintCommand.INSTANCE);
    }

    @Test
    void parseEmptyCommand() {
        Command emptyString = CommandParser.parseInput("");

        assertEquals(emptyString, PrintCommand.INSTANCE);
    }

    @Test
    void parseSpaceCommand() {
        Command spaceString = CommandParser.parseInput(" ");

        assertEquals(spaceString, PrintCommand.INSTANCE);
    }

    @Test
    void parseQuitCommand() {
        Command quit = CommandParser.parseInput("q");

        assertEquals(quit, QuitCommand.INSTANCE);
    }

    @Test
    void parseCoordinate() {
        Command simulationSize = CommandParser.createSimWorld("52");

        SimSetupCommand expectedCommand = new SimSetupCommand("52");

        assertEquals(expectedCommand, simulationSize);
    }

    @Test
    void parseInvalidCoordinate() {
        Command simulationSize = CommandParser.createSimWorld("202");

        SimSetupCommand expectedCommand = new SimSetupCommand("202");

        assertEquals(expectedCommand, simulationSize);
    }

}