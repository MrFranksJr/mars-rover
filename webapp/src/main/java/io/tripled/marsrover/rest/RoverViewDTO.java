package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.domain.rover.RoverBrokenStatus;

public record RoverViewDTO(String roverName, String roverHeading, int roverXPosition, int roverYPosition, int hitPoints, RoverBrokenStatus roverBrokenStatus) {
}
