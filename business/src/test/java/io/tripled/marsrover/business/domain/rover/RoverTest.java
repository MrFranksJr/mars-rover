package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.domain.rover.Rover;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoverTest {
    @Test
    void roverFacesNorthWhenLanded() {
        assertEquals("NORTH", new Rover("R1").getRoverHeading());
    }
}