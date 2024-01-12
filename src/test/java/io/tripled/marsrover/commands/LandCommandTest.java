package io.tripled.marsrover.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import io.tripled.marsrover.simulation.Simulation;
import io.tripled.marsrover.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private SimulationRepository repo;
    private Simulation simWorld;
    private DummyPresenter dummyPresenter;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        simWorld = new Simulation(5);
        repo.add(simWorld);
        dummyPresenter = new DummyPresenter();
    }

    @Test
    void createsRover() {
        Command landCommand = new LandCommand(3, 4, repo);
        landCommand.execute(dummyPresenter);
        assertEquals("R1", simWorld.getRoverList().getFirst().getRoverName());
    }
}