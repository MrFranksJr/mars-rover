package io.tripled.marsrover.business;

import io.tripled.marsrover.api.simulation.SimulationSnapshot;

public interface SimulationQuery {
    SimulationSnapshot getSimulationInformation();

}
