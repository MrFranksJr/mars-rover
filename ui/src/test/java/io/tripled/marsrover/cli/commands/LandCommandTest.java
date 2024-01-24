package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MarsTestConfiguration.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LandCommandTest {
    @Autowired
    private SimulationRepository repo;
    private Simulation simWorld;
    private DummyPresenter dummyPresenter;
    @Autowired
    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        //repo = new InMemSimulationRepo();
        simWorld = new Simulation(10);
        repo.add(simWorld);
        dummyPresenter = new DummyPresenter();
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
        Command landCommand = new LandCommand(new Coordinate(300,700), marsRoverController);

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
        Command landCommand = new LandCommand(new Coordinate(-3, 4), marsRoverController);
        //when
        landCommand.execute(dummyPresenter);

        //then
        assertNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.invalidLandingInstruction());
        assertFalse(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
    }
}