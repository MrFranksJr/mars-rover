package io.tripled.marsrover.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandParserTest {
    private CommandParser commandParser;
    private InMemSimulationRepo inMemSimulationRepo;
    private DummyPresenter dummyPresenter;

    @BeforeEach
    void setUp() {
        inMemSimulationRepo = new InMemSimulationRepo();
        commandParser = new CommandParser(inMemSimulationRepo);
        dummyPresenter = new DummyPresenter();
    }

    @Test
    void parseAnything() {
        Command brol = commandParser.parseInput("brol");

        UnknownCommand expectedCommand = new UnknownCommand("brol");

        assertEquals(brol, expectedCommand);
    }

    @Test
    void parsePrint() {
        Command print = commandParser.parseInput("p");

        assertEquals(print, PrintCommand.INSTANCE);
    }

    @Test
    void parseEmptyCommand() {
        Command emptyString = commandParser.parseInput("");

        assertEquals(emptyString, PrintCommand.INSTANCE);
    }

    @Test
    void parseSpaceCommand() {
        Command spaceString = commandParser.parseInput(" ");

        assertEquals(spaceString, PrintCommand.INSTANCE);
    }

    @Test
    void parseQuitCommand() {
        Command quit = commandParser.parseInput("q");

        assertEquals(quit, QuitCommand.INSTANCE);
    }

    @Test
    void parseCoordinate() {
        Command simulationSize = commandParser.createSimWorld("52").orElseThrow();

        SimSetupCommand expectedCommand = new SimSetupCommand(52, null);

        assertEquals(expectedCommand, simulationSize);
    }

    @Test
    void parseInvalidCoordinate() {
        assertTrue(commandParser.createSimWorld("202").isEmpty());
    }

    @Test
    void canParseFalseLandCommand() {
        Command land = commandParser.parseInput("lan 4 2");

        assertEquals(new UnknownCommand("lan 4 2"), land);
    }

    @Test
    void canParseLandCommand() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("land 4 2");

        assertEquals(new LandCommand(4, 2, inMemSimulationRepo), land);
    }
    @Test
    void canParseLandCommandCapital() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("LANd 4 2");

        assertEquals(new LandCommand(4, 2, inMemSimulationRepo), land);
    }
    @Test
    void canRecognizeHalfLandCommand() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("LANd 4 2");

        assertEquals(new LandCommand(4, 2, inMemSimulationRepo), land);
    }
    @Test
    void recognizesMissingDigit() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        String input = "LANd 4";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }
    @Test
    void recognizesSingleCharacter() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }
    @Test
    void recognizesTwoCharacters() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a b";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }
    @Test
    void recognizesDigitAndCharacter() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        String input = "land 1 b";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }
    @Test
    void recognizesCharacterAndDigit() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a 1";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }
    @Test
    void recognizesNegativeDigitAndPositiveDigit() {
        Command simSetupCommand = new SimSetupCommand(5, inMemSimulationRepo);
        simSetupCommand.execute(dummyPresenter);
        String input = "land -1 2";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }
}