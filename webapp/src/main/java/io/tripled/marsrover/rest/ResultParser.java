package io.tripled.marsrover.rest;

import io.tripled.marsrover.presenters.LandingPresenterImpl;
import io.tripled.marsrover.presenters.LandingState;
import io.tripled.marsrover.presenters.RoverMovePresenterImpl;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;

public enum ResultParser {
    RESULT_PARSER;

    public String moveExecutionResult(InstructionBatch roverInstructionsBatch, RoverMovePresenterImpl roverMovePresenter) {

        Pair<RoverId, RoverMoveState> result = roverMovePresenter.reportRoverMoveResult();
        String roverId = result.first().id();
        RoverMoveState roverMoveState = result.second();

        final boolean roverHasInstructions = !roverInstructionsBatch.batch().isEmpty();

        if (roverHasInstructions && roverMoveState.equals(RoverMoveState.ALREADY_BROKEN))
            return new JsonFactory().createResult("alreadyBroken", roverId);
        if (roverHasInstructions && roverMoveState.equals(RoverMoveState.BROKEN))
            return new JsonFactory().createResult("broken", roverId);
        if (roverHasInstructions && roverMoveState.equals(RoverMoveState.SUCCESS))
            return new JsonFactory().createResult("success", "");
        if (roverHasInstructions && roverMoveState.equals(RoverMoveState.COLLIDED))
            return new JsonFactory().createResult("collided", roverId);

        return new JsonFactory().createResult("error", "");
    }

    public String landExecutionResult(LandingPresenterImpl landingPresenter) {
        LandingState landingState = landingPresenter.reportLandingState();
        if (landingState.equals(LandingState.ON_TOP))
            return new JsonFactory().createResult("onTop", "");
        else if (landingState.equals(LandingState.SUCCESS))
            return new JsonFactory().createResult("success", "");
        else if (landingState.equals(LandingState.MISSES))
            return new JsonFactory().createResult("missed", "");
        else
            return new JsonFactory().createResult("unSuccessful", "");
    }
}
