package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.api.rover.RoverMovePresenter;
import io.tripled.marsrover.api.rover.RoverAlreadyBrokenEvent;
import io.tripled.marsrover.api.rover.RoverBreaksDownEvent;
import io.tripled.marsrover.api.rover.RoverCollidedEvent;
import io.tripled.marsrover.api.rover.RoverMovedSuccessfulEvent;

public enum LogginRoverMovePresenter implements RoverMovePresenter {
    INSTANCE;

    @Override
    public void roverCollided(RoverCollidedEvent r) {
        System.out.println(r.roverState().roverId() + " have collided");
    }

    @Override
    public void roverBreakingDown(RoverBreaksDownEvent r) {
        System.out.println(r.roverState().roverId() + " has broken down");
    }

    @Override
    public void roverAlreadyBrokenDown(RoverAlreadyBrokenEvent r) {
        System.out.println("Rover cannot move since it's broken.");
    }

    @Override
    public void moveRoverSuccessful(RoverMovedSuccessfulEvent r) {
        System.out.println("Rover " + r.roverState().roverId() + " is at " + r.roverState().coordinate() + " facing " + r.roverState().heading());
    }

    @Override
    public void moveRoverError(String simulationId) {
        System.out.println("`This instruction couldn't be executed!\nTry again!");
    }
}
