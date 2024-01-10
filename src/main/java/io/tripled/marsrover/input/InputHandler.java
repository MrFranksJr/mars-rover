package io.tripled.marsrover.input;

import io.tripled.marsrover.commands.Command;
import io.tripled.marsrover.commands.CommandParser;
import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.validators.LandCommandValidator;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Handelt de input van de CLI af
 */
public class InputHandler {

    private final CommandParser commandParser;
    public boolean receivedValidSimSize = false;

    public InputHandler(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    public void handleCommandInput(String commandInput, MessagePresenter presenter) {
        final Command c = commandParser.parseInput(commandInput);
        c.execute(presenter);
    }

    private boolean isValidLandCommand(String commandInput) {
        LandCommandValidator landingValidator = new LandCommandValidator(commandInput);
        return landingValidator.isValid(commandInput);
    }

    public void handleSimulationCreation(String maxCoordinate, MessagePresenter presenter) {
        final Optional<Command> createSimWorld = commandParser.createSimWorld(maxCoordinate);

        Consumer<Command> consumer = command -> {
            command.execute(presenter);
            receivedValidSimSize = true;
        };

        Runnable runnable = () -> presenter.invalidSimSetupMessage(maxCoordinate);

        createSimWorld.ifPresentOrElse(consumer, runnable);
    }

}


