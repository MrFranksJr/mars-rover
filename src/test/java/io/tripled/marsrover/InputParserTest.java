package io.tripled.marsrover;

import io.tripled.marsrover.commands.*;
import io.tripled.marsrover.input.InputParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InputParserTest {
    @Test
    void parseAnything() {
        Command brol = InputParser.parseInput("brol");

        UnknownCommand expectedCommand = new UnknownCommand("brol");

        Assertions.assertEquals(brol, expectedCommand);
    }
    @Test
    void parsePrint() {
        Command print = InputParser.parseInput("p");

        Assertions.assertEquals(print, PrintCommand.INSTANCE);
    }
    @Test
    void parseEmptyCommand() {
        Command emptyString = InputParser.parseInput("");

        Assertions.assertEquals(emptyString, PrintCommand.INSTANCE);
    }
    @Test
    void parseSpaceCommand() {
        Command spaceString = InputParser.parseInput(" ");

        Assertions.assertEquals(spaceString, PrintCommand.INSTANCE);
    }
    @Test
    void parseQuitCommand() {
        Command quit = InputParser.parseInput("q");

        Assertions.assertEquals(quit, QuitCommand.INSTANCE);
    }
    @Test
    void parseCoordinate() {
        Command coordinate = InputParser.parseInput("52");

        SimSetupCommand expectedCommand = new SimSetupCommand("52");

        Assertions.assertEquals(coordinate, expectedCommand);
    }

}