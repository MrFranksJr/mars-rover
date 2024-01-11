package io.tripled.marsrover.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import io.tripled.marsrover.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void verifySimIsCreated() {
        createSimulation(52, presenter);

        assertNotNull(repo.getSimulation());

    }

    private void createSimulation(int number, DummyPresenter presenter) {
        SimSetupCommand simCreationCommand = new SimSetupCommand(number, repo);
        simCreationCommand.execute(presenter);
    }
}