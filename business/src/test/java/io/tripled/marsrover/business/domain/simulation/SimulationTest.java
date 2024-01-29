package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    void returnsCorrectSimSize() {
        Simulation simWorld = Simulation.create(52).orElseThrow();

        assertEquals(52, simWorld.getSimulationSize());
    }

    @Test
    void returnsNrOfCoordinates() {
        Simulation simWorld = Simulation.create(5).orElseThrow();

        assertEquals(36, simWorld.getNrOfCoordinates());
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
                case Simulation.SimulationLandAlreadyPopulated simulationAlreadyPopulated -> fail();
            }
        });
    }

    @Test
    void onlyOneRoverAllowed() {
        final var simWorld = createSimulationWithARoverPresent();

        simWorld.landRover(new Coordinate(1, 1), validateRoverLandingDisallowed());

    }

    @Test
    void moveOneStepForwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.moveRover(Direction.FORWARD);

        assertEquals(6, rover.getRoverYPosition());
    }

    @Test
    void moveOneStepBackwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(4, rover.getRoverYPosition());
    }

    @Test
    void roverTurnsLeft() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.LEFT);

        assertEquals(RoverHeading.WEST, rover.getRoverHeading());
    }

    @Test
    void roverTurnsRight() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.RIGHT);

        assertEquals(RoverHeading.EAST, rover.getRoverHeading());
    }

    @Test
    void roverTurnsLeftAndMoveForward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(RoverHeading.WEST, rover.getRoverHeading());
        assertEquals(4, rover.getRoverXPosition());
    }

    @Test
    void roverTurnsLeftAndMoveBackward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(RoverHeading.WEST, rover.getRoverHeading());
        assertEquals(6, rover.getRoverXPosition());
    }

    @Test
    void roverTurnsRightAndMovesForward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(RoverHeading.EAST, rover.getRoverHeading());
        assertEquals(6, rover.getRoverXPosition());
    }

    @Test
    void roverTurnsRightAndMovesBackwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(RoverHeading.EAST, rover.getRoverHeading());
        assertEquals(4, rover.getRoverXPosition());
    }

    @Test
    void turnRoverToSouthClockwise() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);
        simWorld.turnRover(Direction.RIGHT);
        simWorld.turnRover(Direction.RIGHT);

        assertEquals(RoverHeading.SOUTH, rover.getRoverHeading());
    }

    @Test
    void turnRoverToSouthCounterClockwise() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);
        simWorld.turnRover(Direction.LEFT);
        simWorld.turnRover(Direction.LEFT);

        assertEquals(RoverHeading.SOUTH, rover.getRoverHeading());
    }

    @Test
    void turnRoverToSouthCounterClockwiseAndMoveForward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);
        simWorld.turnRover(Direction.LEFT);
        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(RoverHeading.SOUTH, rover.getRoverHeading());
        assertEquals(4, rover.getRoverYPosition());
    }

    @Test
    void turnRoverToSouthCounterClockwiseAndMoveBackward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 5));
        Rover rover = getRover(simWorld);
        simWorld.turnRover(Direction.LEFT);
        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(RoverHeading.SOUTH, rover.getRoverHeading());
        assertEquals(6, rover.getRoverYPosition());
    }

    @Test
    void roverMovesNorthAndCrossesBoundry() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 10));

        simWorld.moveRover(Direction.FORWARD);

        assertEquals(0, getRover(simWorld).getRoverYPosition());
    }

    @Test
    void roverMovesWestOverBoundry() {
        Simulation simWorld = createSimulation(10, new Coordinate(0, 8));
        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(10, getRover(simWorld).getRoverXPosition());
    }

    @Test
    void roverMovesEastOverBoundary() {
        Simulation simWorld = createSimulation(10, new Coordinate(10, 8));
        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(0, getRover(simWorld).getRoverXPosition());
    }

    @Test
    void roverMovesSouthOverBoundary() {
        Simulation simWorld = createSimulation(10, new Coordinate(10, 0));
        simWorld.turnRover(Direction.RIGHT);
        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(10, getRover(simWorld).getRoverYPosition());
    }

    @Test
    void roverMovesSouthAndCrossesBoundaryBackwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5, 0));

        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(10, getRover(simWorld).getRoverYPosition());
    }

    @Test
    void roverMovesEastOverBoundaryBackward() {
        Simulation simWorld = createSimulation(10, new Coordinate(10, 8));
        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(0, getRover(simWorld).getRoverXPosition());
    }

    @Test
    void roverMovesWestOverBoundaryBackward() {
        Simulation simWorld = createSimulation(10, new Coordinate(0, 8));
        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(10, getRover(simWorld).getRoverXPosition());
    }

    @Test
    void roverMovesNorthOverBoundaryBackward() {
        Simulation simWorld = createSimulation(10, new Coordinate(10, 10));
        simWorld.turnRover(Direction.RIGHT);
        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(0, getRover(simWorld).getRoverYPosition());
    }

    private Simulation createSimulationWithARoverPresent() {
        return createSimulation(5, new Coordinate(3, 4));
    }

    private Simulation.SimulationLandingEventPublisher validateRoverLandingDisallowed() {
        return event -> {
            switch (event) {
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> {
                    fail();
                }
                case Simulation.LandingSuccessfulLandEvent landingSuccessfulEvent -> {
                    fail();
                }
                case Simulation.RoverMissesSimulationLand roverMissesSimulation -> {
                    fail();
                }
                case Simulation.SimulationLandAlreadyPopulated simulationAlreadyPopulated -> {
                    assertTrue(true);
                }
            }
        };
    }

    private Rover getRover(Simulation simWorld) {
        return simWorld.getRoverList().getFirst();
    }

    private Simulation createSimulation(int simulationSize, Coordinate landingCoordinate) {
        final Simulation simWorld = Simulation.create(simulationSize).orElseThrow();
        simWorld.landRover(landingCoordinate, event -> {
        });
        return simWorld;
    }


}