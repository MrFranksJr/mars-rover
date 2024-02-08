package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;

public class RoverMovePresenterImpl implements RoverMovePresenter {
    @Override
    public void moveRoverSuccessful(RoverState roverState) {

    }

    @Override
    public void cannotMoveIfRoverDoesNotExist() {

    }

    @Override
    public String moveRoverUnsuccesful() {
        return "Rover could not execute move instruction. Please provide correct instruction.";
    }
}
