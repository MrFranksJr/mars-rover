package io.tripled.marsrover.api.rover;

import io.tripled.marsrover.api.simulation.SimulationLandEvent;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverMissesSimulationLandEvent(SimulationId id, int simulationSize, Coordinate coordinate) implements SimulationLandEvent {
}
