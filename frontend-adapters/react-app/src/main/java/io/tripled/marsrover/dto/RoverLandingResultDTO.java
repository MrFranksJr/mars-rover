package io.tripled.marsrover.dto;

import io.tripled.marsrover.presenters.LandingState;

public record RoverLandingResultDTO(String simulationId, String roverId, LandingState landingState) {}
