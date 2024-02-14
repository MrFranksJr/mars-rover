package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoverMoveCommandTest {
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController = new MarsRoverController(new InMemSimulationRepo());

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

        Command landCommand = new LandCommand(new Coordinate(5, 5), marsRoverController);
        landCommand.execute(dummyPresenter);
        Command roverMoveCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "f", 1)), marsRoverController);
        return roverMoveCommand;
    }
}