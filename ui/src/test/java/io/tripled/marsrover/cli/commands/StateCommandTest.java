package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.business.api.LandingPresenter;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.RoverMovePresenterChange;
import io.tripled.marsrover.business.api.SimulationStatePresenter;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StateCommandTest {
    private InMemSimulationRepo simulationRepository;
    private Simulation simulation;
    private DummyPresenter dummyPresenter;
    private MarsRoverApi marsRoverController;

    @BeforeEach
    void setUp() {
        simulationRepository = new InMemSimulationRepo();
        simulation = Simulation.create(13).orElseThrow();
        simulationRepository.add(simulation);
        dummyPresenter = new DummyPresenter();
        marsRoverController = new MarsRoverApi() {
            @Override
            public void landRover(Coordinate coordinate, LandingPresenter landingPresenter) {

            }

            @Override
            public void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter) {
                dummyPresenter.stateCommand(simulation.simulationState());
            }

            @Override
            public void initializeSimulation(int simulationSize) {

            }

            @Override
            public void moveRover(List<RoverMove> roverMovesFromString, RoverMovePresenterChange roverMovePresenter) {

            }
        };
    }

    @Test
    void presentsStateCommand() {
        //given
        Command stateCommand = new StateCommand(marsRoverController);

        //when
        stateCommand.execute(dummyPresenter);

        //then
        assertTrue(dummyPresenter.hasStateCommandBeenInvoked());
    }


    @Test
    void checkHeadingOfStateCommand() {
        simulation.landRover(new Coordinate(5, 5), new Simulation.SimulationLandingEventPublisher() {
            @Override
            public void publish(Simulation.SimulationLandEvent event) {

            }
        });
        simulation.turnRover(Direction.RIGHT);

        Command stateCommand = new StateCommand(marsRoverController);
        stateCommand.execute(dummyPresenter);

        assertEquals("EAST", simulation.getRoverList().getFirst().getRoverHeading().toString());
        assertEquals("EAST", dummyPresenter.roverState.roverHeading().toString());
    }
}