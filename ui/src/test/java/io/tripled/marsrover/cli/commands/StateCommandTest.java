package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.*;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class StateCommandTest {
    private MarsRoverApi marsRoverApi;
    private DummyPresenter dummyPresenter;
    private SimSetupCommand simSetupCommand;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        marsRoverApi = new MarsRoverController(new InMemSimulationRepo());
        simSetupCommand = new SimSetupCommand(13, marsRoverApi);
        simSetupCommand.execute(dummyPresenter);
    }

    @Test
    void presentsStateCommand() {
        //given
        Command stateCommand = new StateCommand(marsRoverApi);

        //when
        stateCommand.execute(dummyPresenter);

        //then
        assertTrue(dummyPresenter.hasStateCommandBeenInvoked());
    }
}