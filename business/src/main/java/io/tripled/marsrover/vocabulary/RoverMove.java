package io.tripled.marsrover.vocabulary;

import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.vocabulary.RoverId;
//TODO: Get rid of RoverID
public record RoverMove(RoverId roverId, Direction direction, int steps) {

    public RoverMove(String roverId, String direction, int steps) {
        this(new RoverId(roverId), direction, steps);
    }

    public RoverMove(RoverId roverId, String direction, int steps) {
        this(roverId, Direction.convertTextToDirection(direction), steps);
    }

}