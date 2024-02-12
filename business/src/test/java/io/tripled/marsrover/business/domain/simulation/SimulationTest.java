package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import io.tripled.marsrover.vocabulary.RoverId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    private final RoverId R1 = new RoverId(1);
    private final RoverId R2 = new RoverId(2);

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
    void returnsNrOfCoordinates() {
        Simulation simWorld = Simulation.create(5).orElseThrow();

        assertEquals(36, simWorld.takeSnapshot().totalCoordinates());
    }

    @Test
    void happyLanding() {
        Simulation simWorld = Simulation.create(5).orElseThrow();

        simWorld.landRover(new Coordinate(3, 4), event -> {
            switch (event) {
                case Simulation.LandingSuccessfulLandEvent landingSuccessfulEvent -> {
                    assertEquals(3, landingSuccessfulEvent.roverState().coordinate().xCoordinate());
                    assertEquals(4, landingSuccessfulEvent.roverState().coordinate().yCoordinate());
                }
                case Simulation.RoverMissesSimulationLand roverMissesSimulation -> fail();
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> fail();
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

        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(6, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void moveOneStepBackwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(4, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverTurnsLeft() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.LEFT);

        assertEquals(RoverHeading.WEST, getRover(R1, simWorld).roverHeading());
    }

    @Test
    void roverTurnsRight() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.RIGHT);

        assertEquals(RoverHeading.EAST, getRover(R1, simWorld).roverHeading());
    }

    @Test
    void roverTurnsLeftAndMoveForward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(RoverHeading.WEST, getRover(R1, simWorld).roverHeading());
        assertEquals(4, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverTurnsLeftAndMoveBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(RoverHeading.WEST, getRover(R1, simWorld).roverHeading());
        assertEquals(6, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverTurnsRightAndMovesForward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(RoverHeading.EAST, getRover(R1, simWorld).roverHeading());
        assertEquals(6, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverTurnsRightAndMovesBackwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));

        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(RoverHeading.EAST, getRover(R1, simWorld).roverHeading());
        assertEquals(4, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void turnRoverToSouthClockwise() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.RIGHT);

        assertEquals(RoverHeading.SOUTH, getRover(R1, simWorld).roverHeading());
    }

    @Test
    void turnRoverToSouthCounterClockwise() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.LEFT);

        assertEquals(RoverHeading.SOUTH, getRover(R1, simWorld).roverHeading());
    }

    @Test
    void turnRoverToSouthCounterClockwiseAndMoveForward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(RoverHeading.SOUTH, getRover(R1, simWorld).roverHeading());
        assertEquals(4, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void turnRoverToSouthCounterClockwiseAndMoveBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 5));
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(RoverHeading.SOUTH, getRover(R1, simWorld).roverHeading());
        assertEquals(6, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesNorthAndCrossesBoundry() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 10));

        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesWestOverBoundry() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(0, 8));
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesEastOverBoundary() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 8));
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesSouthOverBoundary() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 0));
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.FORWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesSouthAndCrossesBoundaryBackwards() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(5, 0));

        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void roverMovesEastOverBoundaryBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 8));
        simWorld.moveRover(R1, Direction.LEFT);
        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesWestOverBoundaryBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(0, 8));
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(10, getRover(R1, simWorld).coordinate().xCoordinate());
    }

    @Test
    void roverMovesNorthOverBoundaryBackward() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 10));
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.RIGHT);
        simWorld.moveRover(R1, Direction.BACKWARD);

        assertEquals(0, getRover(R1, simWorld).coordinate().yCoordinate());
    }

    @Test
    void moveSpecificRover() {
        Simulation simWorld = createSimulationWithRover(10, new Coordinate(10, 10));
        landRover(new Coordinate(4, 4), simWorld);
        simWorld.moveRover(R2, Direction.RIGHT);

        assertEquals("R2", getRover(R2, simWorld).roverId());
        assertEquals(RoverHeading.EAST, getRover(R2, simWorld).roverHeading());
    }

    private Simulation createSimulationWithARoverPresent() {
        return createSimulationWithRover(5, new Coordinate(3, 4));
    }

    private Simulation.SimulationLandingEventPublisher validateRoverLandingDisallowed() {
        return event -> {
            switch (event) {
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> {
                    fail();
                }
                case Simulation.LandingSuccessfulLandEvent landingSuccessfulEvent -> {
                    assertTrue(true);
                }
                case Simulation.RoverMissesSimulationLand roverMissesSimulation -> {
                    fail();
                }
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


}