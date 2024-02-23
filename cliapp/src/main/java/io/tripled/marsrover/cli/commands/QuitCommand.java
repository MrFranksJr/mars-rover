package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.cli.messages.MessagePresenter;

enum QuitCommand implements Command {
    INSTANCE;

    @Override
    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.quitMessage();
    }
}
