package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;

public class RoverMovePresenterImpl implements RoverMovePresenter {


    private Pair<RoverId, Boolean> collidedRover = new Pair<>(null, false);
    private Pair<RoverId, Boolean> brokenRover = new Pair<>(null, false);
    private Pair<RoverId, Boolean> roverAlreadyBroken = new Pair<>(null, false);

    @Override
    public void moveRoverSuccessful(RoverState roverState) {
    }


    @Override
    public void roverCollided(RoverState roverState) {
        collidedRover = new Pair<>(roverState.roverId(), true);
    }

    @Override
    public void roverBreakingDown(RoverState roverState) {
        brokenRover = new Pair<>(roverState.roverId(), true);   }

    @Override
    public void roverAlreadyBrokenDown(RoverId roverId) {
        roverAlreadyBroken = new Pair<>(roverId, true);
    }

    public Pair<RoverId, Boolean> hasCollided() {
        return collidedRover;
    }

    public Pair<RoverId, Boolean> isBroken() {
        return brokenRover;
    }

    public Pair<RoverId, Boolean> isAlreadyBroken(){
        return roverAlreadyBroken;
    }
}
