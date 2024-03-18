package io.tripled.marsrover.events;

import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.SimulationId;

public record RoverMissesSimulationLandEvent(SimulationId id, int simulationSize, Coordinate coordinate) implements SimulationLandEvent {
}
