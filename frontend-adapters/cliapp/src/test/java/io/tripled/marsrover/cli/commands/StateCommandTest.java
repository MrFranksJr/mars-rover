package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.*;
import io.tripled.marsrover.api.simulation.SimulationStatePresenter;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class StateCommandTest {
    private MarsRoverApi marsRoverApi;
    private String simulationId;

    @BeforeEach
    void setUp() {
        marsRoverApi = mock(MarsRoverApi.class);
        simulationId = new SimulationId(UUID.randomUUID()).toString();
    }

    @Test
    void presentsStateCommand() {
        //given
        Command stateCommand = new StateCommand(simulationId, marsRoverApi);

        //when
        stateCommand.execute(new DummyPresenter());

        //then
        verify(marsRoverApi).lookUpSimulationState(eq(simulationId), any(SimulationStatePresenter.class));
    }
}