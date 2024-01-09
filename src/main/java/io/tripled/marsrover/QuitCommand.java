package io.tripled.marsrover;

public enum QuitCommand implements Command{
    INSTANCE;
    @Override
    public void execute(MessagePresenter messagePresenter) { messagePresenter.quitMessage(); }
}
