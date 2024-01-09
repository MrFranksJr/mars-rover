package io.tripled.marsrover.input;

import io.tripled.marsrover.commands.CommandParser;
import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.commands.Command;
import io.tripled.marsrover.validators.SimSizeValidator;

import static io.tripled.marsrover.simulation.Simulation.SIM_WORLD;

public class InputHandler {
    public void handleCommandInput(String commandInput, MessagePresenter presenter) {
        final Command c = CommandParser.parseInput(commandInput);

        c.execute(presenter);
    }

    public void handleSimulationCreation(String maxCoordinate, MessagePresenter presenter) {
        if(SimSizeValidator.validateMaxCoordinate(maxCoordinate)) {
            SIM_WORLD.setSimSize(Integer.parseInt(maxCoordinate));
            presenter.simSetupCommand(Integer.parseInt(maxCoordinate), SIM_WORLD.simSize);
        } else {
            presenter.invalidSimSetupCommand(Integer.parseInt(maxCoordinate));
        }
    }

    public boolean receivedValidSimSize = false;
}