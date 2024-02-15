package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.Pair;
import io.tripled.marsrover.vocabulary.RoverId;

public class RoverMovePresenterImpl implements RoverMovePresenter {


    private Pair<RoverId, Boolean> collidedRover = new Pair<>(null, false);

    @Override
    public void moveRoverSuccessful(RoverState roverState) {
    }


    @Override
    public void roverCollided(RoverId roverId) {
        collidedRover = new Pair<>(roverId, true);
    }



    public Pair<RoverId, Boolean> hasCollided() {
        return collidedRover;
    }
}
