package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MarsRoverController implements MarsRoverApi {

    private final SimulationRepository simulationRepository;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    private static void presentLanding(LandingPresenter p, Simulation.SimulationLandEvent e) {
        switch (e) {
            case Simulation.LandingSuccessfulLandEvent l -> p.landingSuccessful(l.roverState());
            case Simulation.RoverMissesSimulationLand r -> p.roverMissesSimulation(r.simulationSize());
            case Simulation.InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.coordinate());
            case Simulation.SimulationLandAlreadyPopulated s -> p.simulationAlreadyPopulated(s.roverState());
        }
    }

    private static void presentRoverMoved(RoverMovePresenter p, Simulation.SimulationMoveRoverEvent e) {
        switch (e) {
            case Simulation.RoverMovedSuccessfulEvent r -> p.moveRoverSuccessful(r.roverState());
        }
    }

    @Override
    public void landRover(Coordinate coordinate, LandingPresenter landingPresenter) {
        if (simulationRepository.getSimulation().isPresent()) {
            final var simulation = simulationRepository.getSimulation().get();

            simulation.landRover(coordinate, event -> presentLanding(landingPresenter, event));
        }

    }

    @Override
    public void moveRover(List<RoverMove> roverMoves, RoverMovePresenter roverMovePresenter) {
        if (simulationRepository.getSimulation().isPresent()) {
            final var simulation = simulationRepository.getSimulation().get();

            simulation.moveRover(roverMoves, event -> presentRoverMoved(roverMovePresenter, event));
        }
    }

    @Override
    public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {
        final Optional<Simulation> simulation = Simulation.create(simulationSize);
        if (simulation.isEmpty())
            simulationCreationPresenter.simulationCreationUnsuccessful(simulationSize);
        else {
            final var simWorld = simulation.get();
            simulationRepository.add(simWorld);
            simulationCreationPresenter.simulationCreationSuccessful(simulation.get().simulationState());
        }
    }

    @Override
    public void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter) {
        final var simulation = simulationRepository.getSimulation();
        if (simulation.isPresent())
            simulationStatePresenter.simulationState(simulation.get().simulationState());
        else
            simulationStatePresenter.simulationState(SimulationState.NONE);
    }
}
