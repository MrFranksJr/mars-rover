package io.tripled.marsrover;

import io.tripled.marsrover.DTOs.SimulationSnapshot;

public interface SimulationCreationPresenter {
    void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot);

    void simulationCreationUnsuccessful(int simulationSize);

}
