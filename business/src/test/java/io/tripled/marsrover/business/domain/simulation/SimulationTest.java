package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.rover.RoverHeading;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    void returnsCorrectSimSize() {
        Simulation simWorld = new Simulation(52);

        assertEquals(52, simWorld.getSimulationSize());
    }

    @Test
    void returnsNrOfCoordinates() {
        Simulation simWorld = new Simulation(5);

        assertEquals(36, simWorld.getNrOfCoordinates());
    }

    @Test
    void happyLanding() {
        Simulation simWorld = new Simulation(5);

        simWorld.landRover(new Coordinate(3,4), event -> {
            switch (event) {
                case Simulation.LandingSuccessfulLandEvent landingSuccessfulEvent -> {
                    assertEquals(3, landingSuccessfulEvent.roverState().xPosition());
                    assertEquals(4, landingSuccessfulEvent.roverState().yPosition());
                }
                case Simulation.RoverMissesSimulationLand roverMissesSimulation -> fail();
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> fail();
                case Simulation.SimulationLandAlreadyPopulated simulationAlreadyPopulated -> fail();
            }
        });
    }

    @Test
    void impossibleLanding() {
        Simulation simWorld = new Simulation(5);

        simWorld.landRover(new Coordinate(-3, 4), event -> {
            switch (event) {
                case Simulation.LandingSuccessfulLandEvent landingSuccessfulEvent -> fail();
                case Simulation.RoverMissesSimulationLand roverMissesSimulation -> fail();
                case Simulation.SimulationLandAlreadyPopulated simulationAlreadyPopulated -> fail();
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> Assertions.assertTrue(true);
            }
        });
    }

    @Test
    void onlyOneRoverAllowed() {
        final var simWorld = createSimulationWithARoverPresent();

        simWorld.landRover(new Coordinate(1, 1), validateRoverLandingDisallowed());

    }

    private static Simulation createSimulationWithARoverPresent() {
        return createSimulation(5, new Coordinate(3,4));
    }

    private static Simulation.SimulationLandingEventPublisher validateRoverLandingDisallowed() {
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

    @Test
    void moveOneStepForwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.moveRover(Direction.FORWARD);

        assertEquals(6, rover.getRoverYPosition());
    }
    @Test
    void moveOneStepBackwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(4, rover.getRoverYPosition());
    }
    @Test
    void roverTurnsLeft() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.LEFT);

        assertEquals(RoverHeading.WEST, rover.getRoverHeading());
    }

    @Test
    void roverTurnsRight() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.RIGHT);

        assertEquals(RoverHeading.EAST, rover.getRoverHeading());
    }

    @Test
    void roverTurnsLeftAndMoveForward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(RoverHeading.WEST, rover.getRoverHeading());
        assertEquals(4, rover.getRoverXPosition());
    }
    @Test
    void roverTurnsLeftAndMoveBackward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.LEFT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(RoverHeading.WEST, rover.getRoverHeading());
        assertEquals(6, rover.getRoverXPosition());
    }

    @Test
    void roverTurnsRightAndMovesForward() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.FORWARD);

        assertEquals(RoverHeading.EAST, rover.getRoverHeading());
        assertEquals(6, rover.getRoverXPosition());
    }
    @Test
    void roverTurnsRightAndMovesBackwards() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);

        simWorld.turnRover(Direction.RIGHT);
        simWorld.moveRover(Direction.BACKWARD);

        assertEquals(RoverHeading.EAST, rover.getRoverHeading());
        assertEquals(4, rover.getRoverXPosition());
    }

    @Test
    void turnRoverToSouthClockwise() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);
        simWorld.turnRover(Direction.RIGHT);
        simWorld.turnRover(Direction.RIGHT);

        assertEquals(RoverHeading.SOUTH, rover.getRoverHeading());
    }
    @Test
    void turnRoverToSouthCounterClockwise() {
        Simulation simWorld = createSimulation(10, new Coordinate(5,5));
        Rover rover = getRover(simWorld);
        simWorld.turnRover(Direction.LEFT);
        simWorld.turnRover(Direction.LEFT);

        assertEquals(RoverHeading.SOUTH, rover.getRoverHeading());
    }

    private static Rover getRover(Simulation simWorld) {
        return simWorld.getRoverList().getFirst();
    }

    private static Simulation createSimulation(int simulationSize, Coordinate landingCoordinate) {
        Simulation simWorld = new Simulation(simulationSize);
        simWorld.landRover(landingCoordinate, event -> {
        });
        return simWorld;
    }


}