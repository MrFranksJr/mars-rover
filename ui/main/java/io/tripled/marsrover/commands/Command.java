package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.simulation.Simulation;

public interface Command {
    void execute(MessagePresenter messagePresenter);
}
