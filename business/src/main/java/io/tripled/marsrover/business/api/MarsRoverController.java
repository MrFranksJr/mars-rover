package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import org.springframework.stereotype.Component;

@Component
public class MarsRoverController implements MarsRoverApi {

    private final SimulationRepository simulationRepository;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }


    @Override
    public void landRover(int xCoordinate, int yCoordinate, LandingPresenter landingPresenter) {
        final var simulation = simulationRepository.getSimulation();

        simulation.landRover(xCoordinate, yCoordinate, event -> presentLanding(landingPresenter, event));
    }

    private static void presentLanding(LandingPresenter p, Simulation.SimulationEvent e) {
        switch (e) {
            case Simulation.LandingSuccessfulEvent l -> p.landingSuccessful(l.roverState());
            case Simulation.RoverMissesSimulation r -> p.roverMissesSimulation(r.simulationSize());
            case Simulation.InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.xCoordinate(), i.yCoordinate());
            case Simulation.SimulationAlreadyPopulated s -> p.simulationAlreadyPopulated(s.roverState());
        }
    }

    @Override
    public void initializeSimulation(int simulationSize) {

    }

    @Override
    public void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter) {
        final var simulation = simulationRepository.getSimulation();

        simulationStatePresenter.simulationState(simulation.simulationState());
    }
}
