package io.tripled.marsrover.business;

import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.vocabulary.SimulationId;

public interface SimulationQuery {
    SimulationSnapshot getSimulationInformation(SimulationId simulationId);

}
