package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;
    private SimulationRepository simulationRepository;
    private final UUID testUUID = UUID.randomUUID();


    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new SimulationRepositoryImpl();
        marsRoverController = new MarsRoverController(simulationRepository);
        Command simSetupCommand = new SimSetupCommand(10, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

    }

    @ParameterizedTest
    @ArgumentsSource(CoordinateProvider.class)
    void landingWasSuccessful(Coordinate landLocation) {
        //given
        Command landCommand = new LandCommand(testUUID.toString(), landLocation, marsRoverController);

        //then
        landCommand.execute(dummyPresenter);

        //when
        assertEquals("R1", dummyPresenter.roverState.roverId().id());
        assertNotNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
        assertFalse(dummyPresenter.invalidLandingInstruction());
        assertEquals(landLocation.xCoordinate(), dummyPresenter.roverState.coordinate().xCoordinate());
        assertEquals(landLocation.yCoordinate(), dummyPresenter.roverState.coordinate().yCoordinate());
    }


    @Test
    void roverMissingSimulation() {
        //given
        Command landCommand = new LandCommand(testUUID.toString(), new Coordinate(300, 700), marsRoverController);

        //when
        landCommand.execute(dummyPresenter);

        //then
        assertNull(dummyPresenter.roverState);
        assertTrue(dummyPresenter.hasRoverMissedSimulationBeenInvoked());
        assertFalse(dummyPresenter.hasRoverLanded());
        assertFalse(dummyPresenter.invalidLandingInstruction());

    }
}