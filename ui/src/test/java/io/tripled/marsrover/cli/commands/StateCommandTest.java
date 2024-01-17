package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateCommandTest {
    private InMemSimulationRepo simulationRepository;
    private Simulation simulation;
    private DummyPresenter dummyPresenter;

    @BeforeEach
    void setUp() {
        simulationRepository = new InMemSimulationRepo();
        simulation = new Simulation(13);
        simulationRepository.add(simulation);
        dummyPresenter = new DummyPresenter();
    }

    @Test
    void presentsStateCommand() {
        //given
        Command stateCommand = new StateCommand(simulationRepository);

        //when
        stateCommand.execute(dummyPresenter);

        //then
        assertTrue(dummyPresenter.hasStateCommandBeenInvoked());
    }
}