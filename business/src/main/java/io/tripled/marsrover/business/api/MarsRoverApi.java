package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.vocabulary.InstructionBatch;

import java.util.List;

public interface MarsRoverApi {

    void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter);

    void landRover(Coordinate coordinate, LandingPresenter landingPresenter);

    void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter);

    //To die
    void moveRover(List<RoverMove> roverMoves, RoverMovePresenter roverMovePresenter);

    //The new way
    void executeMoveInstructions(InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter);
}
