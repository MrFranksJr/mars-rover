package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;

public interface MarsRoverApi {

    void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter);

    void landRover(Coordinate coordinate, LandingPresenter landingPresenter);

    void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter);

    //TODO: The new way
    void executeMoveInstructions(InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter);
}
