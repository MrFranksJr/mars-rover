package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.simulation.Simulation;

public interface Command {
    Simulation execute(MessagePresenter messagePresenter);
}
