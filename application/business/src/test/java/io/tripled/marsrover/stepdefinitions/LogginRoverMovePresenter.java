package io.tripled.marsrover.stepdefinitions;

import io.tripled.marsrover.RoverMovePresenter;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.events.RoverAlreadyBrokenEvent;
import io.tripled.marsrover.events.RoverBreaksDownEvent;
import io.tripled.marsrover.events.RoverCollidedEvent;
import io.tripled.marsrover.events.RoverMovedSuccessfulEvent;

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
