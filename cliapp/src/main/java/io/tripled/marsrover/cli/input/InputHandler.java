package io.tripled.marsrover.cli.input;

import io.tripled.marsrover.cli.commands.Command;
import io.tripled.marsrover.cli.commands.CommandParser;
import io.tripled.marsrover.cli.messages.MessagePresenter;

import java.util.Optional;
import java.util.function.Consumer;

public class InputHandler {
    private final CommandParser commandParser;
    public boolean receivedValidSimSize = false;

    public InputHandler(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    public void handleCommandInput(String commandInput, MessagePresenter presenter) {
        final Command command = commandParser.parseInput(commandInput);
        command.execute(presenter);
    }

    public void handleSimulationCreation(String maxCoordinate, MessagePresenter presenter) {
        final Optional<Command> createSimWorld = commandParser.createSimWorld(maxCoordinate);

        Consumer<Command> consumer = command -> {
            command.execute(presenter);
            receivedValidSimSize = true;
        };

        Runnable runnable = () -> presenter.simulationCreationUnsuccessful(maxCoordinate);

        createSimWorld.ifPresentOrElse(consumer, runnable);
    }
}


