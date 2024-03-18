package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.DummyPresenter;
import io.tripled.marsrover.api.simulation.LandingPresenter;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.cli.messages.MessagePresenter;
import io.tripled.marsrover.vocabulary.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class LandCommandTest {
    private final UUID testUUID = UUID.randomUUID();
    private MessagePresenter dummyPresenter;
    private MarsRoverApi marsRoverControllerMock;

    @BeforeEach
    void setUp() {
        dummyPresenter = new DummyPresenter();
        marsRoverControllerMock = mock(MarsRoverApi.class);
    }

    @Test
    void landRoverInvokedOnApi() {
        //given
        Coordinate coordinate = new Coordinate(5, 10);
        Command landCommand = new LandCommand(testUUID.toString(), coordinate, marsRoverControllerMock);

        //when
        landCommand.execute(dummyPresenter);

        //then
        verify(marsRoverControllerMock).landRover(eq(testUUID.toString()), eq(coordinate), any(LandingPresenter.class));
    }
}