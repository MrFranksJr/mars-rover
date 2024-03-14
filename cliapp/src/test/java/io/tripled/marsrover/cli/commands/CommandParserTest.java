package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.*;
import io.tripled.marsrover.vocabulary.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CommandParserTest {
    private CommandParser commandParser;
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;
    private RoverId R1;
    private RoverId R2;
    private SimulationId simulationId;

    @BeforeEach
    void setUp() {
        marsRoverController = new MarsRoverApi() {
            @Override
            public void landRover(String simulationId, Coordinate coordinate, LandingPresenter landingPresenter) {

            }

            @Override
            public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {

            }

            @Override
            public void lookUpSimulationState(String simulationId, SimulationStatePresenter simulationStatePresenter) {

            }

            @Override
            public void lookUpSimulationStates(SimulationStatePresenter simulationStatePresenter) {

            }

            @Override
            public void executeMoveInstructions(String simulationId, InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {

            }
        };
        commandParser = new CommandParser(marsRoverController);
        dummyPresenter = new DummyPresenter();
        R1 = new RoverId(1);
        R2 = new RoverId(2);
        simulationId = new SimulationId(UUID.randomUUID());
    }

    @Test
    void parseAnything() {
        Command brol = commandParser.parseInput("brol", simulationId);

        UnknownCommand expectedCommand = new UnknownCommand("brol");

        assertEquals(brol, expectedCommand);
    }

    @Test
    void parsePrint() {
        Command print = commandParser.parseInput("p", simulationId);

        assertEquals(print, PrintCommand.INSTANCE);
    }

    @Test
    void introducingStateCommand() {
        marsRoverController.initializeSimulation(10, null);
        Command state = commandParser.parseInput("state", simulationId);

        state.execute(dummyPresenter);
        StateCommand expectedCommand = new StateCommand(simulationId.toString(), marsRoverController);

        assertEquals(expectedCommand, state);
    }

    @Test
    void parseEmptyCommand() {
        Command emptyString = commandParser.parseInput("", simulationId);

        assertEquals(emptyString, PrintCommand.INSTANCE);
    }

    @Test
    void parseSpaceCommand() {
        Command spaceString = commandParser.parseInput(" ", simulationId);

        assertEquals(spaceString, PrintCommand.INSTANCE);
    }

    @Test
    void parseQuitCommand() {
        Command quit = commandParser.parseInput("q", simulationId);

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
        Command land = commandParser.parseInput("lan 4 2", simulationId);

        assertEquals(new UnknownCommand("lan 4 2"), land);
    }

    @Test
    void canParseLandCommand() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("land 4 2", simulationId);

        assertInstanceOf(LandCommand.class, land);
        assertEquals(new LandCommand(simulationId.toString(), new Coordinate(4, 2), null), land);
    }

    @Test
    void canParseLandCommandWithTrailingSpace() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("land 4 2 ", simulationId);

        assertInstanceOf(LandCommand.class, land);
        assertEquals(new LandCommand(simulationId.toString(), new Coordinate(4, 2), null), land);
    }

    @Test
    void canParseLandCommandCapital() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("LANd 4 2", simulationId);

        assertEquals(new LandCommand(simulationId.toString(), new Coordinate(4, 2), null), land);
    }

    @Test
    void canRecognizeHalfLandCommand() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        Command land = commandParser.parseInput("LANd 4 2", simulationId);

        assertEquals(new LandCommand(simulationId.toString(), new Coordinate(4, 2), null), land);
    }

    @Test
    void recognizesMissingDigit() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "LANd 4";
        Command land = commandParser.parseInput(input, simulationId);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesSingleCharacter() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a";
        Command land = commandParser.parseInput(input, simulationId);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesTwoCharacters() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a b";
        Command land = commandParser.parseInput(input, simulationId);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesDigitAndCharacter() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land 1 b";
        Command land = commandParser.parseInput(input, simulationId);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesCharacterAndDigit() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land a 1";
        Command land = commandParser.parseInput(input, simulationId);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void recognizesNegativeDigitAndPositiveDigit() {
        Command simSetupCommand = new SimSetupCommand(5, marsRoverController);
        simSetupCommand.execute(dummyPresenter);
        String input = "land -1 2";
        Command land = commandParser.parseInput(input, simulationId);

        assertEquals(new LandingFailureCommand(input), land);
    }

    @Test
    void parseRoverMoveCommandWithValidForward() {
        Command roverMoveCommand = commandParser.parseInput("R1 f1", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("f", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }


    @Test
    void parseRoverMoveCommandWithValidBackward() {
        Command roverMoveCommand = commandParser.parseInput("R1 b1", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("b", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidLeft() {
        Command roverMoveCommand = commandParser.parseInput("R1 l1", simulationId);
        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("l", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidRight() {
        Command roverMoveCommand = commandParser.parseInput("R1 r1", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("r", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);


        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidForwardNoStepIndication() {
        Command roverMoveCommand = commandParser.parseInput("R1 f", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("f", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidBackwardNoStepIndication() {
        Command roverMoveCommand = commandParser.parseInput("R1 b", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("b", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidLeftNoStepIndication() {
        Command roverMoveCommand = commandParser.parseInput("R1 l", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("l", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithValidRightNoStepIndication() {
        Command roverMoveCommand = commandParser.parseInput("R1 r", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("r", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandMoveForwardSouth() {
        Command roverMoveCommand = commandParser.parseInput("R1 l2 f1", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("l", 2)))
                .addRoverMoves(R1, List.of(new RoverMove("f", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }


    @Test
    void
    parseRoverMoveCommandWithMultipleDirections() {
        Command roverMoveCommand = commandParser.parseInput("R1 f2 b4 r5 l f1", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("f", 2)))
                .addRoverMoves(R1, List.of(new RoverMove("b", 4)))
                .addRoverMoves(R1, List.of(new RoverMove("r", 5)))
                .addRoverMoves(R1, List.of(new RoverMove("l", 1)))
                .addRoverMoves(R1, List.of(new RoverMove("f", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);

    }

    @Test
    void parseRoverR2MoveCommandWithValidRightNoStepIndication() {
        Command roverMoveCommand = commandParser.parseInput("R2 r", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R2, List.of(new RoverMove("r", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandOnlyWithUpperCase() {
        Command roverMoveCommand = commandParser.parseInput("r1 b", simulationId);

        UnknownCommand expectedCommand = new UnknownCommand("r1 b");

        assertEquals(expectedCommand, roverMoveCommand);
    }

    @Test
    void parseRoverMoveCommandWithMultipleRoverIdsSimilarAsMoveInstruction() {
        Command roverMoveCommand = commandParser.parseInput("R1 f2 r2 R2 f1", simulationId);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("f", 2)))
                .addRoverMoves(R1, List.of(new RoverMove("r", 2)))
                .addRoverMoves(R2, List.of(new RoverMove("f", 1)))
                .build();

        RoverMoveCommand expectedCommand = new RoverMoveCommand(simulationId.toString(), instructionBatch, null);

        assertEquals(expectedCommand, roverMoveCommand);
    }
}