package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;

public interface MarsRoverApi {
    void lookUpSimulationState(String simulationId, SimulationStatePresenter simulationStatePresenter);

    void lookUpSimulationStates(SimulationStatePresenter simulationStatePresenter);

    void landRover(String simulationId, Coordinate coordinate, LandingPresenter landingPresenter);

    void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter);

    void executeMoveInstructions(String simulationId, InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter);
}
