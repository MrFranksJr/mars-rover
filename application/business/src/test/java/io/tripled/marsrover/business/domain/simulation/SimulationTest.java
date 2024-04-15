package io.tripled.marsrover.business.domain.simulation;

import com.google.common.collect.ImmutableList;
import io.tripled.marsrover.api.rover.*;
import io.tripled.marsrover.api.simulation.SimulationLandEventPublisher;
import io.tripled.marsrover.api.simulation.SimulationMoveRoverEvent;
import io.tripled.marsrover.api.simulation.SimulationRoverMovedEventPublisher;
import io.tripled.marsrover.vocabulary.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    private final RoverId R1 = new RoverId(1);
    private final RoverId R2 = new RoverId(2);
    private final DummyEventPub dummyEventPub = new DummyEventPub();

    private static void landRover(Coordinate landingCoordinate, Simulation simWorld) {
        simWorld.landRover(landingCoordinate, event -> {
        });
    }

    @Test
    void returnsCorrectSimSize() {
        Simulation simWorld = Simulation.create(52).orElseThrow();

        assertEquals(52, simWorld.takeSnapshot().simulationSize());
    }

    @Test
    void simulationCanHaveAName() {
        Simulation simWorld = Simulation.create("NewSimulation").orElseThrow();

        assertEquals("NewSimulation", simWorld.takeSnapshot().simulationName());
    }

    @Test
    void canCreateSimulationWithNameAndSize() {
        Simulation simWorld = Simulation.create("NewSimulation", 20).orElseThrow();

        assertEquals("NewSimulation", simWorld.takeSnapshot().simulationName());
        assertEquals(20, simWorld.takeSnapshot().simulationSize());
    }

    @Test
    void returnsNrOfCoordinates() {
        Simulation simWorld = Simulation.create(5).orElseThrow();

        assertEquals(36, simWorld.takeSnapshot().totalCoordinates());
    }

    @Test
    void happyLanding() {
        Simulation simWorld = Simulation.create(5).orElseThrow();

        simWorld.landRover(new Coordinate(3, 4), event -> {
            switch (event) {
                case LandingSuccessfulLandEvent landingSuccessfulEvent -> {
                    assertEquals(3, landingSuccessfulEvent.roverState().coordinate().xCoordinate());
                    assertEquals(4, landingSuccessfulEvent.roverState().coordinate().yCoordinate());
                    assertEquals(Heading.NORTH, landingSuccessfulEvent.roverState().heading());
                }
                case RoverMissesSimulationLandEvent roverMissesSimulation -> fail();
                case InvalidCoordinatesReceived invalidCoordinatesReceived -> fail();
                case LandingOnTopEvent landingOnTopEvent -> fail();
                default -> throw new IllegalStateException("Unexpected value: " + event);
            }
        });
    }

    @Test
    void multipleRoversAllowed() {
        final var simWorld = createSimulationWithARoverPresent();

        simWorld.landRover(new Coordinate(1, 1), validateRoverLandingDisallowed());

    }

    @Test
    void moveOneStepForwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(6, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void moveOneStepBackwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(4, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverTurnsLeft() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.LEFT);

        assertEquals(Heading.WEST, getRover(R1, simWorld).heading());
    }

    @Test
    void roverTurnsRight() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.RIGHT);

        assertEquals(Heading.EAST, getRover(R1, simWorld).heading());
    }

    @Test
    void roverTurnsLeftAndMoveForward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(Heading.WEST, getRover(R1, simWorld).heading());
        assertEquals(4, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverTurnsLeftAndMoveBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(Heading.WEST, getRover(R1, simWorld).heading());
        assertEquals(6, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverTurnsRightAndMovesForward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(Heading.EAST, getRover(R1, simWorld).heading());
        assertEquals(6, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverTurnsRightAndMovesBackwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(Heading.EAST, getRover(R1, simWorld).heading());
        assertEquals(4, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void turnRoverToSouthClockwise() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.RIGHT);

        assertEquals(Heading.SOUTH, getRover(R1, simWorld).heading());
    }

    @Test
    void turnRoverToSouthCounterClockwise() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.LEFT);

        assertEquals(Heading.SOUTH, getRover(R1, simWorld).heading());
    }

    @Test
    void turnRoverToSouthCounterClockwiseAndMoveForward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(Heading.SOUTH, getRover(R1, simWorld).heading());
        assertEquals(4, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void turnRoverToSouthCounterClockwiseAndMoveBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(Heading.SOUTH, getRover(R1, simWorld).heading());
        assertEquals(6, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesNorthAndCrossesBoundary() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 10));

        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesWestOverBoundary() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(0, 8));
        moveRover(simWorld, R1, Direction.LEFT);
        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesEastOverBoundary() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 8));
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesSouthOverBoundary() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 0));
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.FORWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesSouthAndCrossesBoundaryBackwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 0));

        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesEastOverBoundaryBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 8));
        moveRover(simWorld, R1, Direction.LEFT);
        ;
        moveRover(simWorld, R1, Direction.BACKWARD);
        ;

        assertEquals(0, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesWestOverBoundaryBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(0, 8));
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesNorthOverBoundaryBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 10));
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.RIGHT);
        moveRover(simWorld, R1, Direction.BACKWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void moveSpecificRover() {
        final Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 10));
        final Coordinate landingCoordinate = new Coordinate(4, 4);
        landRover(landingCoordinate, simWorld);

        moveRover(simWorld, R2, Direction.RIGHT);

        assertEquals(R2, getRover(R2, simWorld).roverId());
        assertEquals(Heading.EAST, getRover(R2, simWorld).heading());
    }

    @Test
    void roverKeepsLastPositionWhenColliding() {
        final Coordinate r1LandingCoordinates = new Coordinate(5, 5);
        final Coordinate r2LandingCoordinates = new Coordinate(5, 4);
        final Simulation simWorld = createSimulationWithRover(10, r1LandingCoordinates);
        landRover(r2LandingCoordinates, simWorld);

        moveRover(simWorld, R2, Direction.FORWARD);

        assertEquals(r2LandingCoordinates, getRover(R2, simWorld).coordinate());
        assertEquals(r1LandingCoordinates, getRover(R1, simWorld).coordinate());
    }

    @Test
    void roverTakesDamageWhenColliding() {
        final Coordinate r1LandingCoordinates = new Coordinate(5, 5);
        final Coordinate r2LandingCoordinates = new Coordinate(5, 4);
        final Simulation simWorld = createSimulationWithRover(10, r1LandingCoordinates);
        landRover(r2LandingCoordinates, simWorld);

        moveRover(simWorld, R2, Direction.FORWARD);

        assertEquals(4, getRover(R2, simWorld).hitpoints());
    }

    //Todo: initial extrapolation done. Need to research further
    @Test
    void listExtrapolation() {
        Simulation simWorld = Simulation.create(52).orElseThrow();
        Pair<RoverId, RoverMove> r1MovePair = new Pair<>(R1, new RoverMove(Direction.FORWARD, 1));
        Pair<RoverId, RoverMove> r2MovePair = new Pair<>(R2, new RoverMove(Direction.FORWARD, 1));

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove(Direction.FORWARD, 3)))
                .addRoverMoves(R2, List.of(new RoverMove(Direction.FORWARD, 3)))
                .build();

        List<List<Pair<RoverId, RoverMove>>> actualInstructionList = simWorld.buildSingleStepInstructions(instructionBatch.batch());

        List<List<Pair<RoverId, RoverMove>>> expectedInstructionList = List.of(List.of(r2MovePair, r2MovePair, r2MovePair), List.of(r1MovePair, r1MovePair, r1MovePair));

        assertEquals(expectedInstructionList, actualInstructionList);
    }

    @Test
    void sequentialHandlingOf2RoversWith1Instruction() {

    }

    private void moveRover(Simulation simulation, RoverId id, Direction direction) {
        final var roverMove = new RoverMove(direction, 1);
        final RoverInstructions roverInstruction = new RoverInstructions(id, ImmutableList.<RoverMove>builder().add(roverMove).build());
        simulation.moveRover(roverInstruction, dummyEventPub);
    }

    private Simulation createSimulationWithARoverPresent() {
        return createSimulationWithRover(5, new Coordinate(3, 4));
    }

    private SimulationLandEventPublisher validateRoverLandingDisallowed() {
        return event -> {
            switch (event) {
                case InvalidCoordinatesReceived invalidCoordinatesReceived -> fail();
                case LandingSuccessfulLandEvent landingSuccessfulEvent -> assertTrue(true);
                case RoverMissesSimulationLandEvent roverMissesSimulation -> fail();
                case LandingOnTopEvent landingOnTopEvent -> fail();
                default -> throw new IllegalStateException("Unexpected value: " + event);
            }
        };
    }

    private RoverState getRover(RoverId roverId, Simulation simWorld) {
        return simWorld.takeSnapshot().getRover(roverId).orElseThrow();
    }

    private Simulation createSimulationWithRover(int simulationSize, Coordinate landingCoordinate) {
        final Simulation simWorld = Simulation.create(simulationSize).orElseThrow();
        landRover(landingCoordinate, simWorld);
        return simWorld;
    }

    static class DummyEventPub implements SimulationRoverMovedEventPublisher {
        @Override
        public void publish(SimulationMoveRoverEvent event) {

        }
    }


}