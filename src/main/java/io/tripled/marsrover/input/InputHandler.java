package io.tripled.marsrover.input;

import io.tripled.marsrover.commands.Command;
import io.tripled.marsrover.commands.CommandParser;
import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.validators.LandCommandValidator;
import io.tripled.marsrover.validators.SimSizeValidator;

public class InputHandler {
    public boolean receivedValidSimSize = false;

    public void handleCommandInput(String commandInput, MessagePresenter presenter) {
        final Command c = CommandParser.parseInput(commandInput);
        c.execute(presenter);
    }

    public void handleSimulationCreation(String maxCoordinate, MessagePresenter presenter) {
        final Command createSimWorld = CommandParser.createSimWorld(maxCoordinate);
        if (SimSizeValidator.validateMaxCoordinate(maxCoordinate)) {
            createSimWorld.execute(presenter);
            receivedValidSimSize = true;
        } else {
            presenter.invalidSimSetupMessage(maxCoordinate);
        }
    }

    private boolean isValidLandCommand(String commandInput) {
        LandCommandValidator landingValidator = new LandCommandValidator(commandInput);
        return landingValidator.isValid(commandInput);
    }
}