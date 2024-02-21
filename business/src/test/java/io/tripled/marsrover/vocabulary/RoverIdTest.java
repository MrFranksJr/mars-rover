package io.tripled.marsrover.vocabulary;

import io.tripled.marsrover.business.ObjectMother;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void canCompareRoverIds() {
        assertTrue(ObjectMother.ROVERSTATE_R1.roverId().isSmaller(ObjectMother.ROVERSTATE_R5.roverId()));
        assertTrue(ObjectMother.ROVERSTATE_R2.roverId().isSmaller(ObjectMother.ROVERSTATE_R3.roverId()));
        assertTrue(ObjectMother.ROVERSTATE_R4.roverId().isBigger(ObjectMother.ROVERSTATE_R2.roverId()));
        assertTrue(ObjectMother.ROVERSTATE_R5.roverId().isBigger(ObjectMother.ROVERSTATE_R3.roverId()));
    }
    @Test
    void compareRoverIds() {
        assertEquals(1, RoverId.COMPARATOR.compare(ObjectMother.R5, ObjectMother.R1));
        assertEquals(1, RoverId.COMPARATOR.compare(ObjectMother.R3, ObjectMother.R2));
        assertEquals(0, RoverId.COMPARATOR.compare(ObjectMother.R2, ObjectMother.R2));
        assertEquals(-1, RoverId.COMPARATOR.compare(ObjectMother.R1, ObjectMother.R4));
        assertEquals(1, RoverId.COMPARATOR.compare(ObjectMother.R6, ObjectMother.R5));
    }
}