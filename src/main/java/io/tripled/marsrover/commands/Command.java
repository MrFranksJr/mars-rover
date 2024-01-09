package io.tripled.marsrover.commands;

import io.tripled.marsrover.MessagePresenter;

public interface Command {
    void execute(MessagePresenter messagePresenter);
}
