package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.vocabulary.RoverId;
//TODO remove roverid please
public record RoverMove(RoverId roverId, Direction direction, int steps) {

    public RoverMove(String roverId, String direction, int steps) {
        this(new RoverId(roverId), direction, steps);
    }

    public RoverMove(RoverId roverId, String direction, int steps) {
        this(roverId, Direction.convertTextToDirection(direction), steps);
    }

}

