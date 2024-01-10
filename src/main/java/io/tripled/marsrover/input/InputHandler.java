package io.tripled.marsrover.input;

import io.tripled.marsrover.commands.CommandParser;
import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.commands.Command;
import io.tripled.marsrover.simulation.Simulation;
import io.tripled.marsrover.validators.SimSizeValidator;

public class InputHandler {
    public void handleCommandInput(String commandInput, MessagePresenter presenter) {
        final Command c = CommandParser.parseInput(commandInput);

        c.execute(presenter);
    }

    public void handleSimulationCreation(String maxCoordinate, MessagePresenter presenter) {
        if(SimSizeValidator.validateMaxCoordinate(maxCoordinate)) {
            Simulation simWorld = new Simulation(Integer.parseInt(maxCoordinate));
            presenter.simSetupCommand(Integer.parseInt(maxCoordinate), simWorld.simSize);
        } else {
            presenter.invalidSimSetupCommand(Integer.parseInt(maxCoordinate));
        }
    }

    public boolean receivedValidSimSize = false;
}