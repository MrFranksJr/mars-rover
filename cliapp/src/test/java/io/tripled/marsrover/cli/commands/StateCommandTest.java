package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.*;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;


class StateCommandTest {
    private MarsRoverApi marsRoverApi;
    private DummyPresenter dummyPresenter;
    private String simulationId;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        SimulationRepository simulationRepository = new SimulationRepositoryImpl();
        marsRoverApi = new MarsRoverControllerImpl(simulationRepository);
        SimSetupCommand simSetupCommand = new SimSetupCommand(13, marsRoverApi);
        simSetupCommand.execute(dummyPresenter);
        simulationId = new SimulationId(UUID.randomUUID()).toString();
    }

    @Test
    void presentsStateCommand() {
        //given
        Command stateCommand = new StateCommand(simulationId, marsRoverApi);

        //when
        stateCommand.execute(dummyPresenter);

        //then
        assertTrue(dummyPresenter.hasStateCommandBeenInvoked());
    }
}