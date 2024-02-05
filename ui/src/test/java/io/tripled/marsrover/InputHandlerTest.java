package io.tripled.marsrover;

import io.tripled.marsrover.cli.commands.CommandParser;
import io.tripled.marsrover.cli.input.InputHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputHandlerTest {
    private InputHandler inputHandler = new InputHandler(new CommandParser(null));

    @Test
    void handleWrongInput() {
        var presenter = new DummyPresenter();

        inputHandler.handleCommandInput("Hallo", presenter);

        assertTrue(presenter.hasUnknownCommandBeenInvoked());
    }

    @Test
    void handlePrintCommand() {
        var presenter = new DummyPresenter();

        inputHandler.handleCommandInput("p", presenter);

        assertFalse(presenter.hasUnknownCommandBeenInvoked());
    }
}
