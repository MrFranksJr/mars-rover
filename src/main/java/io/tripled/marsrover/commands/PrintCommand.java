package io.tripled.marsrover.commands;

import io.tripled.marsrover.MessagePresenter;

public enum PrintCommand implements Command {
    INSTANCE;
    public void execute(MessagePresenter messagePresenter) {
            messagePresenter.printCommand();
    }
}
