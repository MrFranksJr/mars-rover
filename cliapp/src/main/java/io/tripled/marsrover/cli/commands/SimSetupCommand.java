package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.SimulationCreationPresenter;
import io.tripled.marsrover.business.api.SimulationState;
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
            public void simulationCreationSuccessful(SimulationState simulationState) {
                messagePresenter.simulationCreationSuccessful(simulationState);
            }

            @Override
            public void simulationCreationUnsuccessful(int simulationSize) {
                messagePresenter.simulationCreationUnsuccessful(String.valueOf(simulationSize));
            }

            @Override
            public void simulationAlreadyExists(SimulationState simulationState) {
                messagePresenter.duplicateSimulationDetected(simulationState);
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