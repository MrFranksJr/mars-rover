package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverHeading;

public record RoverState(String roverName, RoverHeading roverHeading, Coordinate coordinate) { }