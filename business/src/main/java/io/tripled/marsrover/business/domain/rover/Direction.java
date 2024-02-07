package io.tripled.marsrover.business.domain.rover;

import java.util.List;

public enum Direction {
    FORWARD,
    LEFT, RIGHT, BACKWARD;

    public static Direction convertTextToDirection(String directionInText) {
        return switch (directionInText.toLowerCase()){
            case "f" -> FORWARD;
            case "b" -> BACKWARD;
            case "l" -> LEFT;
            case "r" -> RIGHT;
            default -> FORWARD;
        };
    }
}
