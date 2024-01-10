package io.tripled.marsrover;

import io.tripled.marsrover.commands.CommandParser;
import io.tripled.marsrover.input.InputHandler;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputHandlerTest {
    private InputHandler inputHandler = new InputHandler(new CommandParser(new InMemSimulationRepo()));

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
