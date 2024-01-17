package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Rover;

import java.util.List;

public record SimulationState(int simulationSize, int totalCoordinates, List<Rover> roverList) { }