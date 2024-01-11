package io.tripled.marsrover.rover;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoverTest {
    @Test
    void roverFacesNorthWhenLanded() {
        assertEquals("NORTH", new Rover("R1").getRoverHeading());
    }
}