package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.cli.messages.MessagePresenter;

public interface Command {
    void execute(MessagePresenter messagePresenter);
}
