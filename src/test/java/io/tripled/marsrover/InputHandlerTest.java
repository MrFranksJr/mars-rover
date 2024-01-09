package io.tripled.marsrover;

import io.tripled.marsrover.input.InputHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputHandlerTest {
    private InputHandler inputHandler = new InputHandler();

    @Test
    void handleWrongInput() {
        var presenter = new DummyPresenter();

        inputHandler.handleCommandInput("Hallo", presenter);

        Assertions.assertTrue(presenter.hasUnknownCommandBeenInvoked());
    }

    @Test
    void handlePrintCommand() {
        var presenter = new DummyPresenter();

        inputHandler.handleCommandInput("p", presenter);

        Assertions.assertFalse(presenter.hasUnknownCommandBeenInvoked());
    }
}
