package io.tripled.marsrover.commands;

import io.tripled.marsrover.messages.MessagePresenter;
import io.tripled.marsrover.simulation.InMemSimulationRepo;
import io.tripled.marsrover.simulation.Simulation;
import io.tripled.marsrover.simulation.SimulationRepository;

import java.util.Objects;

public class SimSetupCommand implements Command {
    private final String coordinateInput;
    private final SimulationRepository simRepo;

    public SimSetupCommand(String coordinateInput, SimulationRepository simRepo) {
        this.coordinateInput = coordinateInput;
        this.simRepo = simRepo;
    }

    public void execute(MessagePresenter messagePresenter) {
       //this should already been done
        int maxCoordinate = Integer.parseInt(coordinateInput);
        //create a simulation
        Simulation simWorld = new Simulation(maxCoordinate);
        //save the simulation
        simRepo.add(simWorld);
        //report result of command
        messagePresenter.simSetupMessage(simWorld.getMaxCoordinate(), simWorld.getSimSize());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimSetupCommand that = (SimSetupCommand) o;

        return Objects.equals(coordinateInput, that.coordinateInput);
    }

    @Override
    public int hashCode() {
        return coordinateInput != null ? coordinateInput.hashCode() : 0;
    }
}
