package io.tripled.marsrover.dto;

import io.tripled.marsrover.rest.RoverMoveState;

public record RoverMoveResultDTO(RoverMoveState roverModeState, String roverId) {}
