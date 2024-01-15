package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.simulation.Simulation;

enum QuitCommand implements Command {
    INSTANCE;
    @Override
    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.quitMessage();
    }
}
