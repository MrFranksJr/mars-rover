package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.cli.commands.LandingErrorTypes;
import io.tripled.marsrover.cli.commands.LandingFailureCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LandingErrorTypesTest {
    @BeforeEach
    void setUp() {
        DummyPresenter dummyPresenter = new DummyPresenter();
    }

    @Test
    void recognizesUnableToParseLandingError() {
        String input = "land a";
        LandingFailureCommand landingFailureCommand = new LandingFailureCommand(input);
        assertEquals(LandingErrorTypes.UNABLE_TO_PARSE, landingFailureCommand.getErrorData());
    }

    @Test
    void recognizesNonDigits1() {
        String input = "land a b";
        LandingFailureCommand landingFailureCommand = new LandingFailureCommand(input);
        assertEquals(LandingErrorTypes.RECEIVED_LETTERS, landingFailureCommand.getErrorData());
    }

    @Test
    void recognizesNonDigits2() {
        String input = "land 1 b";
        LandingFailureCommand landingFailureCommand = new LandingFailureCommand(input);
        assertEquals(LandingErrorTypes.RECEIVED_LETTERS, landingFailureCommand.getErrorData());
    }

    @Test
    void recognizesNonDigits3() {
        String input = "land a 1";
        LandingFailureCommand landingFailureCommand = new LandingFailureCommand(input);
        assertEquals(LandingErrorTypes.RECEIVED_LETTERS, landingFailureCommand.getErrorData());
    }

    @Test
    void recognizesNegatives1() {
        String input = "land -1 2";
        LandingFailureCommand landingFailureCommand = new LandingFailureCommand(input);
        assertEquals(LandingErrorTypes.NEGATIVE_INTS, landingFailureCommand.getErrorData());
    }

    @Test
    void recognizesNegatives2() {
        String input = "land 5 -2";
        LandingFailureCommand landingFailureCommand = new LandingFailureCommand(input);
        assertEquals(LandingErrorTypes.NEGATIVE_INTS, landingFailureCommand.getErrorData());
    }
}