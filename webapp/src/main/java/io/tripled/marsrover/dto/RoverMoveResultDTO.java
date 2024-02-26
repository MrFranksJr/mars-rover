package io.tripled.marsrover.dto;

import io.tripled.marsrover.rest.RoverMoveResult;

public record RoverMoveResultDTO(RoverMoveResult roverMove, String roverId) {}
