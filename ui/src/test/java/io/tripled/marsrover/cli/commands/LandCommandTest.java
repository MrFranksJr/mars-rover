package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private SimulationRepository repo;
    private Simulation simWorld;
    private DummyPresenter dummyPresenter;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        simWorld = new Simulation(5);
        repo.add(simWorld);
        dummyPresenter = new DummyPresenter();
    }

    @Test
    void landingWasSuccessful() {
        MarsRoverController marsRoverController = new MarsRoverController(repo);
        //given
        Command landCommand = new LandCommand(3, 4, repo, marsRoverController);
        //then
        landCommand.execute(dummyPresenter);
        //when
        assertEquals("R1", repo.getSimulation().getRoverList().getFirst().getRoverName());
        assertTrue(dummyPresenter.hasLandingCommandBeenInvoked());
        assertEquals(3, dummyPresenter.roverState.xPosition());
        assertEquals(4, dummyPresenter.roverState.yPosition());
        assertEquals("R1", dummyPresenter.roverState.roverName());
    }

    @Test
    void roverMissingSimulation() {
        MarsRoverController marsRoverController = new MarsRoverController(repo);
        //given
        Command landCommand = new LandCommand(3, 7, repo, marsRoverController);
        //when
        landCommand.execute(dummyPresenter);
        //then
        assertNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
    }

    @Disabled
    @Test
    void landingCannotComplete() {
        MarsRoverController marsRoverController = new MarsRoverController(repo);
        //given
        Command landCommand = new LandCommand(-3, 4, repo, marsRoverController);
        //when
        landCommand.execute(dummyPresenter);
        //then
        assertTrue(dummyPresenter.hasLandingFailedCommandBeenInvoked());
    }
}