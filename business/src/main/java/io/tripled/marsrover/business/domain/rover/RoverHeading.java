package io.tripled.marsrover.business.domain.rover;

public enum RoverHeading {
    NORTH,
    EAST ,
    SOUTH,
    WEST ;

    RoverHeading nextClockWise(){
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    RoverHeading nextCounterClockWise() {
        return switch (this) {
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }
}
