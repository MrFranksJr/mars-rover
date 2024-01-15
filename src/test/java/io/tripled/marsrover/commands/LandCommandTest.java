package io.tripled.marsrover.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import io.tripled.marsrover.simulation.Simulation;
import io.tripled.marsrover.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
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
        //given
        Command landCommand = new LandCommand(3, 4, repo);
        //then
        landCommand.execute(dummyPresenter);
        //when
        assertEquals("R1", repo.getSimulation().getRoverList().getFirst().getRoverName());
        assertTrue(dummyPresenter.hasLandingCommandBeenInvoked());
        assertEquals(3, dummyPresenter.roverLandinsPosX);
        assertEquals(4, dummyPresenter.roverLandinsPosY);
        assertEquals("R1", dummyPresenter.rover.getRoverName());
    }

    @Test
    void roverMissingSimulation() {
        //given
        Command landCommand = new LandCommand(3, 7, repo);
        //when
        landCommand.execute(dummyPresenter);
        //then
        assertNull(dummyPresenter.rover);
        assertTrue(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
    }

    @Test
    void landingCannotComplete() {
        //given
        Command landCommand = new LandCommand(-3, 4, repo);
        //when
        landCommand.execute(dummyPresenter);
        //then
        assertTrue(dummyPresenter.hasLandingFailedCommandBeenInvoked());
    }
}