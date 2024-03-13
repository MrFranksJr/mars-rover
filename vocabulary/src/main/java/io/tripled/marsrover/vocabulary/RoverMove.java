package io.tripled.marsrover.vocabulary;

public record RoverMove(Direction direction, int steps) {

    public RoverMove(String direction, int steps) {
        this(Direction.convertTextToDirection(direction), steps);
    }

}