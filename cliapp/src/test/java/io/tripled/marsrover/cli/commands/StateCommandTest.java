package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.dbmodel.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
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
        SimulationRepository simulationRepository = new InMemSimulationRepo();
        marsRoverApi = new MarsRoverController(simulationRepository);
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