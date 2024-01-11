package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.simulation.Simulation;

enum PrintCommand implements Command {
    INSTANCE;
    public Simulation execute(MessagePresenter messagePresenter) {
            messagePresenter.printCommand();
        return null;
    }
}
