package io.tripled.marsrover.business.domain.simulation;

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
        simWorld.landRover(3, 4, event -> {
            switch (event) {
                case Simulation.LandingSuccessfulEvent landingSuccessfulEvent -> {
                    assertEquals(3, landingSuccessfulEvent.roverState().xPosition());
                    assertEquals(4, landingSuccessfulEvent.roverState().yPosition());
                }
                case Simulation.RoverMissesSimulation roverMissesSimulation -> fail();
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> fail();
                case Simulation.SimulationAlreadyPopulated simulationAlreadyPopulated -> fail();
            }
        });
    }

    @Test
    void impossibleLanding() {
        Simulation simWorld = new Simulation(5);
        simWorld.landRover(-3, 4, event -> {
            switch (event) {
                case Simulation.LandingSuccessfulEvent landingSuccessfulEvent -> fail();
                case Simulation.RoverMissesSimulation roverMissesSimulation -> fail();
                case Simulation.SimulationAlreadyPopulated simulationAlreadyPopulated -> fail();
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> Assertions.assertTrue(true);
            }
        });
    }

    @Test
    void onlyOneRoverAllowed() {
        final var simWorld = createSimulationWithARoverPresent();

        simWorld.landRover(1, 1, validateRoverLandingDisallowed());

    }

    private static Simulation createSimulationWithARoverPresent() {
        Simulation simWorld = new Simulation(5);
        simWorld.landRover(3, 4, event -> {
        });
        return simWorld;
    }

    private static Simulation.SimulationEventPublisher validateRoverLandingDisallowed() {
        return event -> {
            switch (event) {
                case Simulation.InvalidCoordinatesReceived invalidCoordinatesReceived -> {
                    fail();
                }
                case Simulation.LandingSuccessfulEvent landingSuccessfulEvent -> {
                    fail();
                }
                case Simulation.RoverMissesSimulation roverMissesSimulation -> {
                    fail();
                }
                case Simulation.SimulationAlreadyPopulated simulationAlreadyPopulated -> {
                    assertTrue(true);
                }
            }
        };
    }
}