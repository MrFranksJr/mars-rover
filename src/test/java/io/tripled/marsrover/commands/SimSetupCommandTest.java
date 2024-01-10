package io.tripled.marsrover.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import io.tripled.marsrover.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimSetupCommandTest {
    private SimulationRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
    }

    @Test
    void verifySimIsCreated() {
        var presenter = new DummyPresenter();

        createSimulation("52", presenter);

        assertNotNull(repo.getSimulation());

    }

    private void createSimulation(String number, DummyPresenter presenter) {
        SimSetupCommand simCreationCommand = new SimSetupCommand(number, repo);
        simCreationCommand.execute(presenter);
    }
}