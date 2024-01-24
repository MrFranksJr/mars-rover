package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {MarsTestConfiguration.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RoverMoveCommandTest {
    @Autowired
    private SimulationRepository repo;
    private Simulation simWorld;
    private DummyPresenter dummyPresenter;
    @Autowired
    private MarsRoverApi marsRoverController;

    @Test
    void parseMoveCommandStringR1f2(){
        simWorld = new Simulation(10);
        repo.add(simWorld);
        dummyPresenter = new DummyPresenter();

        Command landCommand = new LandCommand(new Coordinate(5,5), marsRoverController);

        //then
        landCommand.execute(dummyPresenter);

        Command roverMoveCommand = new RoverMoveCommand(List.of(new RoverMove("R1", "f", 1)), marsRoverController);

        roverMoveCommand.execute(dummyPresenter);
        assertEquals("R1", dummyPresenter.roverState.roverName());
        assertEquals(5, dummyPresenter.roverState.coordinate().xCoordinate());
        assertEquals(6, dummyPresenter.roverState.coordinate().yCoordinate());



    }

}