package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.vocabulary.RoverId;

public record RoverMove(RoverId roverId, String direction, int steps) {

    public RoverMove(String roverId, String direction, int steps) {
        this(new RoverId(roverId), direction, steps);
    }
}
