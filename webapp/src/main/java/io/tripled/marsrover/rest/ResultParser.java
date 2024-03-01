package io.tripled.marsrover.rest;

import io.tripled.marsrover.dto.RoverLandingResultDTO;
import io.tripled.marsrover.dto.RoverMoveResultDTO;
import io.tripled.marsrover.dto.SimulationCreationDTO;
import io.tripled.marsrover.presenters.LandingPresenterImpl;
import io.tripled.marsrover.presenters.LandingState;
import io.tripled.marsrover.presenters.RoverMovePresenterImpl;
import io.tripled.marsrover.presenters.SimulationCreationPresenterImpl;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;
import io.tripled.marsrover.vocabulary.SimulationId;

public enum ResultParser {
    RESULT_PARSER;

    public SimulationCreationDTO simulationCreationResult(SimulationCreationPresenterImpl simulationCreationPresenter) {
        String simulationId = simulationCreationPresenter.reportSimulationState().id().toString();

        return new SimulationCreationDTO(simulationId);
    }

    public RoverMoveResultDTO moveExecutionResult(InstructionBatch roverInstructionsBatch, RoverMovePresenterImpl roverMovePresenter) {
        Pair<RoverId, RoverMoveResult> result = roverMovePresenter.reportRoverMoveResult();
        String simulationId = roverMovePresenter.getSimulationId().toString();
        RoverMoveResult roverMoveState;
        String roverId;

        if (!roverInstructionsBatch.batch().isEmpty()) {
            roverMoveState = result.second();
            roverId = result.first().id();
        }
        else {
            roverMoveState = RoverMoveResult.ERROR;
            roverId = "";
        }

        return new RoverMoveResultDTO(simulationId, roverMoveState, roverId);
    }

    public RoverLandingResultDTO landExecutionResult(LandingPresenterImpl landingPresenter) {
        LandingState landingState = landingPresenter.reportLandingState();
        String simulationId = landingPresenter.simulationId().toString();
        String roverId = "MISSED";

        if (landingPresenter.roverId() != null) {
            roverId = landingPresenter.roverId().toString();
        }

        return new RoverLandingResultDTO(simulationId, roverId, landingState);
    }
}
