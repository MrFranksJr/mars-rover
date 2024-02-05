package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        marsRoverController = new MarsRoverController(new InMemSimulationRepo());
        Command simSetupCommand = new SimSetupCommand(10, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

    }

    @ParameterizedTest
    @ArgumentsSource(CoordinateProvider.class)
    void landingWasSuccessful(Coordinate landLocation) {
        //given
        Command landCommand = new LandCommand(landLocation, marsRoverController);

        //then
        landCommand.execute(dummyPresenter);

        //when
        assertEquals("R1", dummyPresenter.roverState.roverName());
        assertNotNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
        assertFalse(dummyPresenter.invalidLandingInstruction());
        assertEquals(landLocation.xCoordinate(), dummyPresenter.roverState.coordinate().xCoordinate());
        assertEquals(landLocation.yCoordinate(), dummyPresenter.roverState.coordinate().yCoordinate());
        assertEquals("R1", dummyPresenter.roverState.roverName());
    }

    @Test
    void onlyOneRoverAllowed() {
        Coordinate landLocation = new Coordinate(1, 1);
        //given
        Command landFirstCommand = new LandCommand(landLocation, marsRoverController);
        Command landSecondCommand = new LandCommand(landLocation, marsRoverController);
        landFirstCommand.execute(dummyPresenter);

        //then
        landSecondCommand.execute(dummyPresenter);

        //when
        assertTrue(dummyPresenter.wasAlreadyRoverPresentInvoked());
    }

    @Test
    void roverMissingSimulation() {
        //given
        Command landCommand = new LandCommand(new Coordinate(300, 700), marsRoverController);

        //when
        landCommand.execute(dummyPresenter);

        //then
        assertNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
        assertFalse(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.invalidLandingInstruction());

    }
}