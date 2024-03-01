package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.business.dbmodel.InMemSimulationRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoverMoveCommandTest {
    private DummyPresenter dummyPresenter;
    private SimulationRepository simulationRepository;
    private MarsRoverApi marsRoverController;
    private RoverId R1;
    private UUID testUUID = UUID.randomUUID();

    @BeforeEach
    void init() {
        this.R1 = new RoverId("R1");
        simulationRepository = new InMemSimulationRepo();
        marsRoverController = new MarsRoverController(simulationRepository);
    }

    @Test
    void simpleRoverMoveInvocation() {
        final Command roverMoveCommand = setUpSimWorldAndSimpleMoveCommand();

        roverMoveCommand.execute(dummyPresenter);

        assertTrue(dummyPresenter.hasRoverMoved());
    }

    @Test
    void parseMoveCommandStringR1f2() {
        final Command roverMoveCommand = setUpSimWorldAndSimpleMoveCommand();

        roverMoveCommand.execute(dummyPresenter);
        assertEquals("R1", dummyPresenter.roverState.roverId().id());
        assertEquals(5, dummyPresenter.roverState.coordinate().xCoordinate());
        assertEquals(6, dummyPresenter.roverState.coordinate().yCoordinate());
        assertTrue(dummyPresenter.hasRoverMoved());
    }

    private Command createSimulationOfSize10() {
        dummyPresenter = new DummyPresenter();
        Command simSetupCommand = new SimSetupCommand(10, marsRoverController);
        return simSetupCommand;
    }


    private Command setUpSimWorldAndSimpleMoveCommand() {
        final Command simSetupCommand = createSimulationOfSize10();
        simSetupCommand.execute(dummyPresenter);

        Command landCommand = new LandCommand(testUUID.toString(), new Coordinate(5, 5), marsRoverController);
        landCommand.execute(dummyPresenter);

        final InstructionBatch instructionBatch = InstructionBatch.newBuilder()
                .addRoverMoves(R1, List.of(new RoverMove("f", 1)))
                .build();
        return new RoverMoveCommand(testUUID.toString(),instructionBatch, marsRoverController);
    }
}