package io.tripled.marsrover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {
    @Test
    void parseAnything() {
        Command brol = Parser.parseInput("brol");

        UnknownCommand expectedCommand = new UnknownCommand("brol");

        Assertions.assertEquals(brol, expectedCommand);
    }
    @Test
    void parsePrint() {
        Command print = Parser.parseInput("p");

        Assertions.assertEquals(print, PrintCommand.INSTANCE);
    }
    @Test
    void parseEmptyCommand() {
        Command emptyString = Parser.parseInput("");

        Assertions.assertEquals(emptyString, PrintCommand.INSTANCE);
    }
    @Test
    void parseSpaceCommand() {
        Command spaceString = Parser.parseInput(" ");

        Assertions.assertEquals(spaceString, PrintCommand.INSTANCE);
    }
    @Test
    void parseQuitCommand() {
        Command quit = Parser.parseInput("q");

        Assertions.assertEquals(quit, QuitCommand.INSTANCE);
    }
    @Test
    void parseCoordinate() {
        Command coordinate = Parser.parseInput("52");

        SimSetupCommand expectedCommand = new SimSetupCommand("52");

        Assertions.assertEquals(coordinate, expectedCommand);
    }

}