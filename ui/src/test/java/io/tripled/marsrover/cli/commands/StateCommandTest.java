package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateCommandTest {
    private InMemSimulationRepo simulationRepository;
    private Simulation simulation;
    private DummyPresenter dummyPresenter;
    private MarsRoverController marsRoverController;

    @BeforeEach
    void setUp() {
        simulationRepository = new InMemSimulationRepo();
        simulation = new Simulation(13);
        simulationRepository.add(simulation);
        dummyPresenter = new DummyPresenter();
        marsRoverController = new MarsRoverController(simulationRepository);
    }

    @Test
    void presentsStateCommand() {
        //given
        Command stateCommand = new StateCommand(marsRoverController);

        //when
        stateCommand.execute(dummyPresenter);

        //then
        assertTrue(dummyPresenter.hasStateCommandBeenInvoked());
    }
}