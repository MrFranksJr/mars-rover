package io.tripled.marsrover.input;

import io.tripled.marsrover.MessagePresenter;
import io.tripled.marsrover.commands.Command;

public class InputHandler {

    public void handleInput(String input, MessagePresenter presenter) {
        //Parse input
        final Command c = InputParser.parseInput(input);
        //Voer command out
        c.execute(presenter);
        //Print resultaat uit
    }

}