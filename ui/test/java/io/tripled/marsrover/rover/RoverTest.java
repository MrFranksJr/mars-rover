package io.tripled.marsrover.rover;

import io.tripled.marsrover.Rover;
import org.junit.jupiter.api.Test;

class RoverTest {
    @Test
    void roverFacesNorthWhenLanded() {
        assertEquals("NORTH", new Rover("R1").getRoverHeading());
    }
}