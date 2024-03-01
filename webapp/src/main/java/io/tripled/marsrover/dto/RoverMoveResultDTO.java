package io.tripled.marsrover.dto;

import io.tripled.marsrover.rest.RoverMoveResult;

public record RoverMoveResultDTO(String simulationId, RoverMoveResult roverMove, String roverId) {}
