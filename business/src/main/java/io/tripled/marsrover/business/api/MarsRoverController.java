package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverInstructions;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MarsRoverController implements MarsRoverApi {

    private final SimulationRepository simulationRepository;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    @Override
    public void landRover(Coordinate coordinate, LandingPresenter landingPresenter) {
        if (simulationRepository.getSimulation().isPresent()) {

            final var simulation = simulationRepository.getSimulation().get();
            final var eventPublisher = createEventPublisher(landingPresenter);
            simulation.landRover(coordinate, eventPublisher);
        }
    }

    @Override
    public void executeMoveInstructions(InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {
        if (simulationRepository.getSimulation().isPresent()) {
            final var simulation = simulationRepository.getSimulation().get();

            for (RoverInstructions roverInstructions : instructionBatch.batch()) {
                simulation.moveRover(roverInstructions, event -> presentRoverMoved(roverMovePresenter, event));
            }

            //TODO:cleanup
            //simulation.moveRovers(instructionBatch.batch());
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
            simulationCreationPresenter.simulationCreationSuccessful(simulation.get().takeSnapshot());
        }
    }

    @Override
    public void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter) {
        final var simulation = simulationRepository.getSimulation();
        if (simulation.isPresent())
            simulationStatePresenter.simulationState(simulation.get().takeSnapshot());
        else
            simulationStatePresenter.simulationState(SimulationSnapshot.NONE);
    }

    private static Simulation.SimulationLandingEventPublisher createEventPublisher(LandingPresenter landingPresenter) {
        return event -> presentLanding(landingPresenter, event);
    }

    private static void presentLanding(LandingPresenter p, Simulation.SimulationLandEvent e) {
        switch (e) {
            case Simulation.LandingSuccessfulLandEvent l -> p.landingSuccessful(l.roverState());
            case Simulation.RoverMissesSimulationLand r -> p.roverMissesSimulation(r.simulationSize());
            case Simulation.InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.coordinate());
            case Simulation.LandingOnTopEvent l -> p.landingOnTop(l);
        }
    }

    private static void presentRoverMoved(RoverMovePresenter p, Simulation.SimulationMoveRoverEvent e) {
        switch (e) {
            case Simulation.RoverMovedSuccessfulEvent r -> p.moveRoverSuccessful(r.roverState());
            case Simulation.RoverCollided roverCollided -> p.roverCollided(roverCollided.roverState());
            case Simulation.RoverDeath roverDeath -> { p.roverBreakingDown(roverDeath.roverState());
            }
            case Simulation.RoverAlreadyBroken roverAlreadyBroken -> { p.roverAlreadyBrokenDown(roverAlreadyBroken.roverId());
            }
        }
    }
}
