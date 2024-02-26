package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.api.SimulationSnapshot;

public interface SimulationQuery {
    SimulationSnapshot getSimulationInformation();

}
