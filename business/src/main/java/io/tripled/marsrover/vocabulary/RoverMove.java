package io.tripled.marsrover.vocabulary;

import io.tripled.marsrover.business.domain.rover.Direction;

public record RoverMove(Direction direction, int steps) {

    public RoverMove(String direction, int steps) {
        this(Direction.convertTextToDirection(direction), steps);
    }

}