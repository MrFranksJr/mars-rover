package io.tripled.marsrover.api.rover;

import io.tripled.marsrover.api.simulation.SimulationLandEvent;
import io.tripled.marsrover.vocabulary.SimulationId;

public record LandingSuccessfulLandEvent(SimulationId id, RoverState roverState) implements SimulationLandEvent {
}
