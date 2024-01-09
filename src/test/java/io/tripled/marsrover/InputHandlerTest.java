package io.tripled.marsrover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputHandlerTest {
    private InputHandler inputHandler = new InputHandler();

    @Test
    void handleWrongInput() {
        var presenter = new DummyPresenter();

        inputHandler.handleInput("Hallo", presenter);

        Assertions.assertTrue(presenter.hasUnknownCommandBeenInvoked());
    }

    @Test
    void handlePrintCommand() {
        var presenter = new DummyPresenter();

        inputHandler.handleInput("p", presenter);

        Assertions.assertFalse(presenter.hasUnknownCommandBeenInvoked());
    }
}
