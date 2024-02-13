package io.tripled.marsrover.vocabulary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoverIdTest {
    @Test
    void invalidRoverId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> new RoverId("Guido is een heel toffe jongen"));
    }

    @Test
    void allTextValidRoverId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> new RoverId("RGuido"));
    }

    @Test
    void validRoverId() {
        new RoverId("R1");
        new RoverId("R6");
        new RoverId("R987");
    }
}