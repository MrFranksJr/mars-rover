package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;

enum PrintCommand implements Command {
    INSTANCE;
    public void execute(MessagePresenter messagePresenter) {
            messagePresenter.printCommand();
    }
}
