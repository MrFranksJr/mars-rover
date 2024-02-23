package io.tripled.marsrover.dto;

import io.tripled.marsrover.business.domain.rover.OperationalStatus;

public record RoverViewDTO(String roverName, String roverHeading, int roverXPosition, int roverYPosition, int hitPoints, OperationalStatus operationalStatus) {
}