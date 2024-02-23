package io.tripled.marsrover.rest;

import io.tripled.marsrover.dto.RoverLandingResultDTO;
import io.tripled.marsrover.dto.RoverMoveResultDTO;
import io.tripled.marsrover.presenters.LandingPresenterImpl;
import io.tripled.marsrover.presenters.LandingState;
import io.tripled.marsrover.presenters.RoverMovePresenterImpl;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;

public enum ResultParser {
    RESULT_PARSER;

    public RoverMoveResultDTO moveExecutionResult(InstructionBatch roverInstructionsBatch, RoverMovePresenterImpl roverMovePresenter) {

        Pair<RoverId, RoverMoveState> result = roverMovePresenter.reportRoverMoveResult();

        RoverMoveState roverMoveState = !roverInstructionsBatch.batch().isEmpty() ? result.second() : RoverMoveState.ERROR;
        String roverId = !roverInstructionsBatch.batch().isEmpty() ? result.first().id() : "";

        return new RoverMoveResultDTO(roverMoveState,roverId);
    }

    public RoverLandingResultDTO landExecutionResult(LandingPresenterImpl landingPresenter) {
        LandingState landingState = landingPresenter.reportLandingState();
        return new RoverLandingResultDTO(landingState);
    }
}
