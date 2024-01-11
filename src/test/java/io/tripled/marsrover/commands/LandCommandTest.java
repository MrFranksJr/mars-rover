package io.tripled.marsrover.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import io.tripled.marsrover.simulation.Simulation;
import io.tripled.marsrover.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private SimulationRepository repo;
    private Simulation simWorld;
    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        simWorld = new Simulation(5);
    }

    @Test
    void createsRover() {

    }
}