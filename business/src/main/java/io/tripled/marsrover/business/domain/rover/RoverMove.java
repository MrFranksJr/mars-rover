package io.tripled.marsrover.business.domain.rover;

import io.tripled.marsrover.business.domain.rover.Direction;

public record RoverMove(String roverId, String direction, int steps) {
}
