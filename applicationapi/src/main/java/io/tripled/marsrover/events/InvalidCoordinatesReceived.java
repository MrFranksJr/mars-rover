package io.tripled.marsrover.events;

import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.SimulationId;

public record InvalidCoordinatesReceived(SimulationId id, Coordinate coordinate) implements SimulationLandEvent {
}
