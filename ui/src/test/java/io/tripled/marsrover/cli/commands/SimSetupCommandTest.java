package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.commands.SimSetupCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimSetupCommandTest {
    private SimulationRepository repo;
    private DummyPresenter presenter;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        presenter = new DummyPresenter();
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        createSimulation(52, presenter);

        Simulation simulation = repo.getSimulation();

        assertNotNull(repo.getSimulation());
        assertEquals(52, simulation.getSimulationSize());
    }

    private void createSimulation(int number, DummyPresenter presenter) {
        SimSetupCommand simCreationCommand = new SimSetupCommand(number, repo);
        simCreationCommand.execute(presenter);
    }
}