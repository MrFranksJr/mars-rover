package io.tripled.marsrover.dto;

import io.tripled.marsrover.rest.RoverMoveState;

public record RoverMoveDTO(RoverMoveState roverModeState, String roverId) {
}
