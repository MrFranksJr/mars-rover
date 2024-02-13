package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.vocabulary.InstructionBatch;

import java.util.List;

public interface MarsRoverApi {

    void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter);

    void landRover(Coordinate coordinate, LandingPresenter landingPresenter);

    void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter);

    void moveRover(List<RoverMove> roverMoves, RoverMovePresenter roverMovePresenter);

    void executeMoveInstructions(InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter);
}
