package io.tripled.marsrover.vocabulary;

public enum Direction {
    FORWARD, LEFT, RIGHT, BACKWARD;

    public static Direction convertTextToDirection(String directionInText) {
        return switch (directionInText.toLowerCase()) {
            case "f" -> FORWARD;
            case "b" -> BACKWARD;
            case "l" -> LEFT;
            case "r" -> RIGHT;
            default -> FORWARD;
        };
    }
}
