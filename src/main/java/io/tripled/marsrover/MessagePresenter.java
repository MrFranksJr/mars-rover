package io.tripled.marsrover;

public interface MessagePresenter {
    void unknownCommand(String input);

    void printCommand();
}
