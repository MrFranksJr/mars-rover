package io.tripled.marsrover.simulation;

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
}