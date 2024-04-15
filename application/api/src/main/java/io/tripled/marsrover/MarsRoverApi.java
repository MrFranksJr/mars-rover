package io.tripled.marsrover;

import io.tripled.marsrover.api.simulation.LandingPresenter;
import io.tripled.marsrover.api.simulation.SimulationCreationPresenter;
import io.tripled.marsrover.api.simulation.SimulationStatePresenter;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.api.rover.RoverMovePresenter;

public interface MarsRoverApi {
    void lookUpSimulationState(String simulationId, SimulationStatePresenter simulationStatePresenter);

    void lookUpSimulationStates(SimulationStatePresenter simulationStatePresenter);

    void landRover(String simulationId, Coordinate coordinate, LandingPresenter landingPresenter);

    void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter);

    void initializeSimulation(String simulationName, SimulationCreationPresenter simulationCreationPresenter);

    void executeMoveInstructions(String simulationId, InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter);
}
