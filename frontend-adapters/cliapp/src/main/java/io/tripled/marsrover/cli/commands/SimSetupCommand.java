package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.api.simulation.SimulationCreationPresenter;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.cli.messages.MessagePresenter;

public class SimSetupCommand implements Command {
    private final MarsRoverApi marsRoverApi;
    private final int coordinateInput;

    public SimSetupCommand(int coordinateInput, MarsRoverApi marsRoverApi) {
        this.coordinateInput = coordinateInput;
        this.marsRoverApi = marsRoverApi;
    }

    public void execute(MessagePresenter messagePresenter) {
        marsRoverApi.initializeSimulation(coordinateInput, new SimulationCreationPresenter() {
            @Override
            public void simulationCreationSuccessful(SimulationSnapshot simulationSnapshot) {
                messagePresenter.simulationCreationSuccessful(simulationSnapshot);
            }

            @Override
            public void simulationCreationUnsuccessful(int simulationSize) {
                messagePresenter.simulationCreationUnsuccessful(String.valueOf(simulationSize));
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimSetupCommand that = (SimSetupCommand) o;

        return coordinateInput == that.coordinateInput;
    }

    @Override
    public int hashCode() {
        return coordinateInput;
    }
}
