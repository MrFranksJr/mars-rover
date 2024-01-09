package io.tripled.marsrover;

public class InputHandler {

    void handleInput(String input, MessagePresenter presenter) {
        //Parse input
        final Command c = Parser.parseInput(input);
        //Voer command out
        c.execute(presenter);
        //Print resultaat uit
    }

}