package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.SimulationDocumentRepositoryImpl;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationDocumentRepository;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimSetupCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;
    private SimulationRepository simulationRepository;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new InMemSimulationRepo();
        SimulationDocumentRepository dummySimulationDocumentRepository = new SimulationDocumentRepositoryImpl();
        marsRoverController = new MarsRoverController(simulationRepository, dummySimulationDocumentRepository);
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        Command simSetupCommand = new SimSetupCommand(52, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

        assertTrue(dummyPresenter.hasSimulationBeenCreated());
        assertEquals(52, dummyPresenter.simulationSnapshot.simulationSize());
    }
}