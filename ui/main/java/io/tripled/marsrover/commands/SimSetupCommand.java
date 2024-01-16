package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsroverbusiness.simulation.Simulation;
import io.tripled.marsroverbusiness.simulation.SimulationRepository;

public class SimSetupCommand implements Command {
    private final int coordinateInput;
    private final SimulationRepository simRepo;

    public SimSetupCommand(int coordinateInput, SimulationRepository simRepo) {
        this.coordinateInput = coordinateInput;
        this.simRepo = simRepo;
    }

    public void execute(MessagePresenter messagePresenter) {
        Simulation simWorld = new Simulation(coordinateInput);
        simRepo.add(simWorld);
        messagePresenter.simSetupMessage(simWorld.getSimulationSize(), simWorld.getNrOfCoordinates());
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
