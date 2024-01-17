package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private SimulationRepository repo;
    private Simulation simWorld;
    private DummyPresenter dummyPresenter;
    private MarsRoverController marsRoverController;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        simWorld = new Simulation(10);
        repo.add(simWorld);
        dummyPresenter = new DummyPresenter();
        marsRoverController = new MarsRoverController(repo);
    }

    @ParameterizedTest
    @ArgumentsSource(CoordinateProvider.class)
    void landingWasSuccessful(Coordinate landLocation) {
        //given
        Command landCommand = new LandCommand(landLocation.x(), landLocation.y(), marsRoverController);

        //then
        landCommand.execute(dummyPresenter);

        //when
        assertEquals("R1", dummyPresenter.roverState.roverName());
        assertNotNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
        assertFalse(dummyPresenter.invalidLandingInstruction());
        assertEquals(landLocation.x(), dummyPresenter.roverState.xPosition());
        assertEquals(landLocation.y(), dummyPresenter.roverState.yPosition());
        assertEquals("R1", dummyPresenter.roverState.roverName());
    }

    @Test
    void onlyOneRoverAllowed() {
        Coordinate landLocation = new Coordinate(1, 1);
        //given
        Command landFirstCommand = new LandCommand(landLocation.x(), landLocation.y(), marsRoverController);
        Command landSecondCommand = new LandCommand(landLocation.x(), landLocation.y(), marsRoverController);
        landFirstCommand.execute(dummyPresenter);

        //then
        landSecondCommand.execute(dummyPresenter);

        //when
        assertTrue(dummyPresenter.wasAlreadyRoverPresentInvoked());
    }

    @Test
    void roverMissingSimulation() {
        //given
        Command landCommand = new LandCommand(300, 700, marsRoverController);

        //when
        landCommand.execute(dummyPresenter);

        //then
        assertNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
        assertFalse(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.invalidLandingInstruction());

    }

    @Test
    void landingCannotComplete() {

        //given
        Command landCommand = new LandCommand(-3, 4, marsRoverController);
        //when
        landCommand.execute(dummyPresenter);

        //then
        assertNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.invalidLandingInstruction());
        assertFalse(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
    }
}