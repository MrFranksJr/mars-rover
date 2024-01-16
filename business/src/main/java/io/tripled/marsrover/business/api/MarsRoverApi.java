package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.simulation.SimulationRepository;

public interface MarsRoverApi {
    RoverState landRover(int xCoordinate, int yCoordinate);
}
