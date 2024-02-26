package io.tripled.marsrover.presenters;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.rest.RoverMoveResult;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;

public class RoverMovePresenterImpl implements RoverMovePresenter {

    private Pair<RoverId, RoverMoveResult> result = new Pair<>(null, RoverMoveResult.NONE);

    @Override
    public void moveRoverSuccessful(RoverState roverState) {
        result = new Pair<>(roverState.roverId(), RoverMoveResult.SUCCESS);
    }

    @Override
    public void roverCollided(RoverState roverState) {
        result = new Pair<>(roverState.roverId(), RoverMoveResult.COLLIDED);
    }

    @Override
    public void roverBreakingDown(RoverState roverState) {
        result = new Pair<>(roverState.roverId(), RoverMoveResult.BROKEN);
    }

    @Override
    public void roverAlreadyBrokenDown(RoverId roverId) {
        result = new Pair<>(roverId, RoverMoveResult.ALREADY_BROKEN);
    }

    public Pair<RoverId, RoverMoveResult> reportRoverMoveResult() {
        return result;
    }
}
