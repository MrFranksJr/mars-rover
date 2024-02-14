package io.tripled.marsrover.business.domain.rover;

public enum Direction {
    FORWARD, LEFT, RIGHT, BACKWARD;

    public static Direction convertTextToDirection(String directionInText) {
        return switch (directionInText) {
            case "f" -> FORWARD;
            case "b" -> BACKWARD;
            case "l" -> LEFT;
            case "r" -> RIGHT;
            default -> FORWARD;
        };
    }
}
