package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.cli.messages.MessagePresenter;

enum PrintCommand implements Command {
    INSTANCE;
    public void execute(MessagePresenter messagePresenter) {
            messagePresenter.printCommand();
    }
}
