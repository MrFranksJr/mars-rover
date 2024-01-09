package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;

enum QuitCommand implements Command{
    INSTANCE;
    @Override
    public void execute(MessagePresenter messagePresenter) {
        messagePresenter.quitMessage();
    }
}
