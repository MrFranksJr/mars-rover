package io.tripled.marsrover.presenters;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.rest.RoverMoveState;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;

public class RoverMovePresenterImpl implements RoverMovePresenter {

    private Pair<RoverId, RoverMoveState> result = new Pair<>(null, RoverMoveState.NONE);

    @Override
    public void moveRoverSuccessful(RoverState roverState) {
        result = new Pair<>(roverState.roverId(), RoverMoveState.SUCCESS);
    }

    @Override
    public void roverCollided(RoverState roverState) {
        result = new Pair<>(roverState.roverId(), RoverMoveState.COLLIDED);
    }

    @Override
    public void roverBreakingDown(RoverState roverState) {
        result = new Pair<>(roverState.roverId(), RoverMoveState.BROKEN);
    }

    @Override
    public void roverAlreadyBrokenDown(RoverId roverId) {
        result = new Pair<>(roverId, RoverMoveState.ALREADY_BROKEN);
    }

    public Pair<RoverId, RoverMoveState> reportRoverMoveResult() {
        return result;
    }
}
