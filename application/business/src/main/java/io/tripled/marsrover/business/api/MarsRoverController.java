package io.tripled.marsrover.business.api;

import io.tripled.marsrover.*;
import io.tripled.marsrover.api.simulation.*;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.api.rover.InvalidCoordinatesReceived;
import io.tripled.marsrover.api.rover.LandingOnTopEvent;
import io.tripled.marsrover.api.rover.LandingSuccessfulLandEvent;
import io.tripled.marsrover.api.rover.RoverAlreadyBrokenEvent;
import io.tripled.marsrover.api.rover.RoverBreaksDownEvent;
import io.tripled.marsrover.api.rover.RoverCollidedEvent;
import io.tripled.marsrover.api.rover.RoverMissesSimulationLandEvent;
import io.tripled.marsrover.api.rover.RoverMovedSuccessfulEvent;
import io.tripled.marsrover.vocabulary.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverInstructions;
import io.tripled.marsrover.vocabulary.SimulationId;
import io.tripled.marsrover.api.rover.RoverMovePresenter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class MarsRoverController implements MarsRoverApi {
    private final SimulationRepository simulationRepository;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    private static SimulationLandEventPublisher createEventPublisher(LandingPresenter landingPresenter) {
        return event -> presentLanding(landingPresenter, event);
    }

    private static void presentLanding(LandingPresenter p, SimulationLandEvent e) {
        switch (e) {
            case LandingSuccessfulLandEvent l -> p.landingSuccessful(l);
            case RoverMissesSimulationLandEvent r -> p.roverMissesSimulation(r);
            case InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.coordinate());
            case LandingOnTopEvent l -> p.landingOnTop(l);
            default -> throw new IllegalStateException("Unexpected value: " + e);
        }
    }

    private static void presentRoverMoved(RoverMovePresenter p, SimulationMoveRoverEvent e) {
        switch (e) {
            case RoverMovedSuccessfulEvent r -> p.moveRoverSuccessful(r);
            case RoverCollidedEvent r -> p.roverCollided(r);
            case RoverBreaksDownEvent r -> p.roverBreakingDown(r);
            case RoverAlreadyBrokenEvent r -> p.roverAlreadyBrokenDown(r);
            default -> throw new IllegalStateException("Unexpected value: " + e);
        }
    }

    @Override
    public void landRover(String simulationId, Coordinate coordinate, LandingPresenter landingPresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<SimulationSnapshot> simulationSnapshot = simulationRepository.getSimulation(simId);
        Simulation simulation = Simulation.of(simulationSnapshot.orElseThrow());
        final var eventPublisher = createEventPublisher(landingPresenter);
        simulation.landRover(coordinate, eventPublisher);

        simulationRepository.save(simulation.takeSnapshot());
    }

    @Override
    public void executeMoveInstructions(String simulationId, InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<SimulationSnapshot> simulationSnapshot = simulationRepository.getSimulation(simId);
        Simulation simulation = Simulation.of(simulationSnapshot.orElseThrow());

        if (instructionBatch.batch().isEmpty())
            roverMovePresenter.moveRoverError(simulationId);
        else {
            for (RoverInstructions roverInstructions : instructionBatch.batch()) {
                simulation.moveRover(roverInstructions, event -> presentRoverMoved(roverMovePresenter, event));
            }
            simulationRepository.save(simulation.takeSnapshot());
        }

    }

    @Override
    public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {
        final Optional<Simulation> simulation = Simulation.create(simulationSize);
        if (simulation.isEmpty())
            simulationCreationPresenter.simulationCreationUnsuccessful(simulationSize);
        else {
            final var simWorld = simulation.orElseThrow();
            simulationRepository.add(simWorld.takeSnapshot());
            simulationCreationPresenter.simulationCreationSuccessful(simWorld.takeSnapshot());
        }
    }

    @Override
    public void lookUpSimulationState(String simulationId, SimulationStatePresenter simulationStatePresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<SimulationSnapshot> simulationSnapshot = simulationRepository.getSimulation(simId);
        Simulation simulation = Simulation.of(simulationSnapshot.orElseThrow());
        simulationStatePresenter.simulationState(simulation.takeSnapshot());
    }

    @Override
    public void lookUpSimulationStates(SimulationStatePresenter simulationStatePresenter) {
        Optional<List<SimulationSnapshot>> existingSimulationSnapshots = simulationRepository.getSimulationSnapshots();
        if (existingSimulationSnapshots.isPresent()) {
            //TODO: remove if okay
            //REMOVED STREAM CREATION OF SIMULATIONS, SINCE WE NEED TO PASS A LIST OF SNAPSHOTS ANYHOW
            /*List<Simulation> simulations = existingSimulationSnapshots.orElseThrow().stream()
                    .map(Simulation::of)
                    .toList();*/
            simulationStatePresenter.simulationState(existingSimulationSnapshots.get());
        } else simulationStatePresenter.simulationState(Collections.emptyList());
    }
}