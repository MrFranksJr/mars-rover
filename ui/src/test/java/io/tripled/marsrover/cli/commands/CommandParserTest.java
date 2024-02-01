package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.*;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
    private CommandParser commandParser;
    private InMemSimulationRepo repo;
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        marsRoverController = new MarsRoverApi() {
            @Override
            public void landRover(Coordinate coordinate, LandingPresenter landingPresenter) {

            }

            @Override
            public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {

            }

            @Override
            public void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter) {

            }

            @Override
            public void moveRover(List<RoverMove> roverMovesFromString, RoverMovePresenter roverMovePresenter) {

            }
        };
        commandParser = new CommandParser(repo, marsRoverController);
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
    void introducingStateCommand() {
        Simulation simWorld = Simulation.create(10).orElseThrow();
        repo.add(simWorld);
        Command state = commandParser.parseInput("state");

        state.execute(dummyPresenter);
        StateCommand expectedCommand = new StateCommand(marsRoverController);

        assertEquals(expectedCommand, state);
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

        SimSetupCommand expectedCommand = new SimSetupCommand(52, marsRoverController);

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
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("land 4 2");

        assertInstanceOf(LandCommand.class, land);
        assertEquals(new LandCommand(new Coordinate(4,2), null), land);
    }

    @Test
    void canParseLandCommandWithTrailingSpace() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("land 4 2 ");

        assertInstanceOf(LandCommand.class, land);
        assertEquals(new LandCommand(new Coordinate(4,2), null), land);
    }

    @Test
    void canParseLandCommandCapital() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("LANd 4 2");

        assertEquals(new LandCommand(new Coordinate(4,2), null), land);
    }

    @Test
    void canRecognizeHalfLandCommand() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("LANd 4 2");

        assertEquals(new LandCommand(new Coordinate(4,2), null), land);
    }

    @Test
    void recognizesMissingDigit() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "LANd 4";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesSingleCharacter() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesTwoCharacters() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a b";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesDigitAndCharacter() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land 1 b";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesCharacterAndDigit() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a 1";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesNegativeDigitAndPositiveDigit() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land -1 2";
        Command land = commandParser.parseInput(input);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void parseRoverMoveCommandWithValidForward(){
        Command roverMoveCommand = commandParser.parseInput("R1 f1");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "f", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidBackward(){
        Command roverMoveCommand = commandParser.parseInput("R1 b1");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "b", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidLeft(){
        Command roverMoveCommand = commandParser.parseInput("R1 l1");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "l", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidRight(){
        Command roverMoveCommand = commandParser.parseInput("R1 r1");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "r", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidForwardNoStepIndication(){
        Command roverMoveCommand = commandParser.parseInput("R1 f");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "f", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidBackwardNoStepIndication(){
        Command roverMoveCommand = commandParser.parseInput("R1 b");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "b", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidLeftNoStepIndication(){
        Command roverMoveCommand = commandParser.parseInput("R1 l");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "l", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidRightNoStepIndication(){
        Command roverMoveCommand = commandParser.parseInput("R1 r");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "r", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandMoveForwardSouth(){
        Command roverMoveCommand = commandParser.parseInput("R1 l2 f1");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(
                new RoverMove("R1", "l", 2),
                new RoverMove("R1", "f", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }


    @Test
    void
    parseRoverMoveCommandWithMultipleDirections(){
        Command roverMoveCommand = commandParser.parseInput("R1 f2 b4 r5 l f1");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(
                new RoverMove("R1", "f", 2),
                new RoverMove("R1", "b", 4),
                new RoverMove("R1", "r", 5),
                new RoverMove("R1", "l", 1),
                new RoverMove("R1", "f", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);

    }

    @Test
    void parseRoverMoveCommandWithLowerCase(){
        Command roverMoveCommand = commandParser.parseInput("r1 b");

        RoverMoveCommand expectedCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "b", 1)), null);

        assertEquals(expectedCommand, roverMoveCommand);
    }


}