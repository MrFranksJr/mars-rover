package io.tripled.marsrover.dto;

import io.tripled.marsrover.business.domain.rover.HealthState;

public record RoverViewDTO(String roverName, String roverHeading, int roverXPosition, int roverYPosition, int hitPoints, HealthState operationalStatus) {
}