package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class StateCommandTest {
    private MarsRoverApi marsRoverApi;
    private DummyPresenter dummyPresenter;
    private SimSetupCommand simSetupCommand;
    private SimulationRepository simulationRepository;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new InMemSimulationRepo();
        marsRoverApi = new MarsRoverController(simulationRepository);
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