package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;

public enum StateCommand implements Command {
    INSTANCE;

    @Override
    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.stateCommand();
    }
}
