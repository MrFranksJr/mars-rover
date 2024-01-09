package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;

public interface Command {
    void execute(MessagePresenter messagePresenter);
}
