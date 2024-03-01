package io.tripled.marsrover.business.stepdefinitions;

import io.tripled.marsrover.business.api.RoverMovePresenter;
import io.tripled.marsrover.business.domain.simulation.Simulation;

public enum LogginRoverMovePresenter implements RoverMovePresenter {
    INSTANCE;

    @Override
    public void moveRoverSuccessful(Simulation.RoverMovedSuccessfulEvent r) {
        System.out.println("Rover " + r.roverState().roverId() + " is at " + r.roverState().coordinate() + " facing " + r.roverState().roverHeading());
    }

    @Override
    public void roverCollided(Simulation.RoverCollidedEvent r) {
        System.out.println(r.roverState().roverId() + " have collided");
    }

    @Override
    public void roverBreakingDown(Simulation.RoverBreaksDownEvent r) {
        System.out.println(r.roverState().roverId() + " has broken down");
    }

    @Override
    public void roverAlreadyBrokenDown(Simulation.RoverAlreadyBrokenEvent r) {

    }
}
