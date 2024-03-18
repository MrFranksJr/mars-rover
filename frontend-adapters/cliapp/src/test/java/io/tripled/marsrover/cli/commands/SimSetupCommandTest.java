package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.api.simulation.SimulationCreationPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SimSetupCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        marsRoverController = mock(MarsRoverApi.class);
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        Command simSetupCommand = new SimSetupCommand(52, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

        verify(marsRoverController).initializeSimulation(eq(52), any(SimulationCreationPresenter.class));
    }
}