package io.tripled.marsrover;

public enum PrintCommand implements Command {
    INSTANCE;
    public void execute(MessagePresenter messagePresenter) {
            messagePresenter.printCommand();
    }
}
