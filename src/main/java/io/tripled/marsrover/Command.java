package io.tripled.marsrover;

public interface Command {
    void execute(MessagePresenter messagePresenter);
}
