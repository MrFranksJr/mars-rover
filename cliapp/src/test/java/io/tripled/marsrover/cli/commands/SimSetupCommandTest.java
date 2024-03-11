package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimSetupCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;
    private SimulationRepository simulationRepository;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new SimulationRepository() {
            @Override
            public void add(SimulationSnapshot snapshot) {

            }

            @Override
            public void save(SimulationSnapshot snapshot) {

            }

            @Override
            public Optional<List<SimulationSnapshot>> retrieveSimulations() {
                return Optional.empty();
            }

            @Override
            public Optional<SimulationSnapshot> getSimulation(SimulationId simulationId) {
                return Optional.empty();
            }
        };
        marsRoverController = new MarsRoverController(simulationRepository);
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        Command simSetupCommand = new SimSetupCommand(52, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

        assertTrue(dummyPresenter.hasSimulationBeenCreated());
        assertEquals(52, dummyPresenter.simulationSnapshot.simulationSize());
    }
}