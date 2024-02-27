package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.SimulationDocumentRepositoryImpl;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationDocumentRepository;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.*;

class LandCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;
    private SimulationRepository simulationRepository;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new InMemSimulationRepo();
        SimulationDocumentRepository dummySimulationDocumentRepository = new SimulationDocumentRepositoryImpl();
        marsRoverController = new MarsRoverController(simulationRepository,dummySimulationDocumentRepository);
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