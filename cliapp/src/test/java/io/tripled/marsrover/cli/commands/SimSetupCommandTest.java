package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimSetupCommandTest {
    private DummyPresenter dummyPresenter;

    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        marsRoverController = new MarsRoverController(new InMemSimulationRepo());
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        Command simSetupCommand = new SimSetupCommand(52, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

        assertTrue(dummyPresenter.hasSimulationBeenCreated());
        assertEquals(52, dummyPresenter.simulationSnapshot.simulationSize());
    }
}