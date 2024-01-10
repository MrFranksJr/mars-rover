package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.rover.Rover;

public enum LandCommand implements Command {
    INSTANCE;
    @Override
    public void execute(MessagePresenter messagePresenter) {
        Rover r1 = new Rover();
        System.out.println("COMMAND TO LAND!");
    }
}
