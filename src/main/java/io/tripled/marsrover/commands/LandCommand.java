package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.rover.Rover;
import io.tripled.marsrover.simulation.Simulation;

public enum LandCommand implements Command {
    INSTANCE;

    @Override
    public Simulation execute(MessagePresenter messagePresenter) {
        Rover r1 = new Rover();
        messagePresenter.landRoverMessage();
        return null;
    }
}
