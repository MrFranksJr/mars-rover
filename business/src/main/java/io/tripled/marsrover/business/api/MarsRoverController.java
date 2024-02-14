package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.Direction;
import io.tripled.marsrover.vocabulary.RoverMove;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverInstructions;
import org.springframework.stereotype.Component;

import java.util.List;
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

    private InstructionBatch parseRoverMovesListToInstructionBatch(List<RoverMove> roverMoves) {
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();

        for(RoverMove move : roverMoves) {
            instructionBatch.addRoverMoves(move.roverId().id(), move);
        }
        return instructionBatch.build();
    }

    @Override
    public void executeMoveInstructions(InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {
        if (simulationRepository.getSimulation().isPresent()) {
            final var simulation = simulationRepository.getSimulation().get();
            for (RoverInstructions roverInstructions : instructionBatch.batch()) {
                simulation.moveRover(roverInstructions.moves(), event -> presentRoverMoved(roverMovePresenter, event));
            }
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
            simulationStatePresenter.simulationState(SimulationState.NONE);
    }

    private static Simulation.SimulationLandingEventPublisher createEventPublisher(LandingPresenter landingPresenter) {
        return event -> presentLanding(landingPresenter, event);
    }

    private static void presentLanding(LandingPresenter p, Simulation.SimulationLandEvent e) {
        switch (e) {
            case Simulation.LandingSuccessfulLandEvent l -> p.landingSuccessful(l.roverState());
            case Simulation.RoverMissesSimulationLand r -> p.roverMissesSimulation(r.simulationSize());
            case Simulation.InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.coordinate());
        }
    }

    private static void presentRoverMoved(RoverMovePresenter p, Simulation.SimulationMoveRoverEvent e) {
        switch (e) {
            case Simulation.RoverMovedSuccessfulEvent r -> p.moveRoverSuccessful(r.roverState());
            case Simulation.RoverCollided roverCollided -> p.roverCollided(roverCollided.roverId());
        }
    }
}
