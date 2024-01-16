package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}