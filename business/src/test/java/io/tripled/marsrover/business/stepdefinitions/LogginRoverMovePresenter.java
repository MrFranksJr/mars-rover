package io.tripled.marsrover.business.stepdefinitions;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.vocabulary.RoverId;

public enum LogginRoverMovePresenter implements RoverMovePresenter {
    INSTANCE;

    @Override
    public void moveRoverSuccessful(RoverState roverState) {
        System.out.println("Rover " + roverState.roverId() + " is at " + roverState.coordinate() + " facing " + roverState.roverHeading());
    }

    @Override
    public void roverCollided(RoverState roverState) {
        System.out.println(roverState.roverId() + " have collided");
    }

    @Override
    public void roverBreakingDown(RoverState roverState) {
        System.out.println(roverState.roverId() + " has died");
    }

    @Override
    public void roverAlreadyBrokenDown(RoverId roverId) {

    }
}
