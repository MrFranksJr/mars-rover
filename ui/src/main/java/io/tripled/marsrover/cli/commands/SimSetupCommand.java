package io.tripled.marsrover.cli.commands;

import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.messages.MessagePresenter;

public class SimSetupCommand implements Command {
    private final int coordinateInput;
    private final SimulationRepository simRepo;

    public SimSetupCommand(int coordinateInput, SimulationRepository simRepo) {
        this.coordinateInput = coordinateInput;
        this.simRepo = simRepo;
    }

    public void execute(MessagePresenter messagePresenter) {
        final var simulation = Simulation.create(coordinateInput);
        if (simulation.isEmpty())
            messagePresenter.invalidSimSetupMessage(coordinateInput + "");
        else {
            final var simWorld = simulation.get();
            simRepo.add(simWorld);
            messagePresenter.simSetupMessage(simWorld.getSimulationSize(), simWorld.getNrOfCoordinates());
        }
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
