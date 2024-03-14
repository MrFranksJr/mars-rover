package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DTOs.SimulationSnapshot;
import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.MarsRoverControllerImpl;
import io.tripled.marsrover.SimulationRepository;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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
            public Optional<List<SimulationSnapshot>> getSimulationSnapshots() {
                return Optional.empty();
            }

            @Override
            public Optional<SimulationSnapshot> getSimulationSnapshot(SimulationId simulationId) {
                return Optional.empty();
            }
        };
        marsRoverController = new MarsRoverControllerImpl(simulationRepository);
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        Command simSetupCommand = new SimSetupCommand(52, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

        assertTrue(dummyPresenter.hasSimulationBeenCreated());
    }
}