package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MarsTestConfiguration.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimSetupCommandTest {
    private SimulationRepository repo;
    private DummyPresenter dummyPresenter;
    @Autowired
    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        repo = new InMemSimulationRepo();
        dummyPresenter = new DummyPresenter();
    }

    @Test
    void simulationIsSuccessfullyCreated() {
        Command simSetupCommand = new SimSetupCommand(52, marsRoverController);
        simSetupCommand.execute(dummyPresenter);

        assertTrue(dummyPresenter.hasSimulationBeenCreated());
        assertEquals(52, dummyPresenter.simulationState.simulationSize());
    }
}