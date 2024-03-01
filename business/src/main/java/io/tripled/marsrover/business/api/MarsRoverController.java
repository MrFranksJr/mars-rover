package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverInstructions;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class MarsRoverController implements MarsRoverApi {
    private final SimulationRepository simulationRepository;
    //TODO Get true SimulationId from the simulation (instead of generating new one)
    private SimulationId simulationId;

    public MarsRoverController(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    private static Simulation.SimulationLandingEventPublisher createEventPublisher(LandingPresenter landingPresenter) {
        return event -> presentLanding(landingPresenter, event);
    }

    private static void presentLanding(LandingPresenter p, Simulation.SimulationLandEvent e) {
        switch (e) {
            case Simulation.LandingSuccessfulLandEvent l -> p.landingSuccessful(l);
            case Simulation.RoverMissesSimulationLandEvent r -> p.roverMissesSimulation(r);
            case Simulation.InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.coordinate());
            case Simulation.LandingOnTopEvent l -> p.landingOnTop(l);
        }
    }

    private static void presentRoverMoved(RoverMovePresenter p, Simulation.SimulationMoveRoverEvent e) {
        switch (e) {
            case Simulation.RoverMovedSuccessfulEvent r -> p.moveRoverSuccessful(r);
            case Simulation.RoverCollidedEvent r -> p.roverCollided(r);
            case Simulation.RoverBreaksDownEvent r -> p.roverBreakingDown(r);
            case Simulation.RoverAlreadyBrokenEvent r ->
                    p.roverAlreadyBrokenDown(r);
        }
    }

    @Override
    public void landRover(String simulationId, Coordinate coordinate, LandingPresenter landingPresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<Simulation> simulation = simulationRepository.getSimulation(simId);
            final var eventPublisher = createEventPublisher(landingPresenter);
            simulation.orElseThrow().landRover(coordinate, eventPublisher);

            simulationRepository.save(simulation.get());
    }

    @Override
    public void executeMoveInstructions(String simulationId, InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<Simulation> simulation = simulationRepository.getSimulation(simId);

            for (RoverInstructions roverInstructions : instructionBatch.batch()) {
                simulation.orElseThrow().moveRover(roverInstructions, event -> presentRoverMoved(roverMovePresenter, event));
            }
            simulationRepository.save(simulation.orElseThrow());
    }

    @Override
    public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {
        final Optional<Simulation> simulation = Simulation.create(simulationSize);
        if (simulation.isEmpty())
            simulationCreationPresenter.simulationCreationUnsuccessful(simulationSize);
        else {
            simulationId = simulation.get().takeSnapshot().id();
            final var simWorld = simulation.orElseThrow();
            simulationRepository.add(simWorld);
            simulationCreationPresenter.simulationCreationSuccessful(simWorld.takeSnapshot());
        }
    }

    @Override
    public void lookUpSimulationState(String simulationId, SimulationStatePresenter simulationStatePresenter) {
        SimulationId simId = new SimulationId(UUID.fromString(simulationId));
        Optional<Simulation> simulation = simulationRepository.getSimulation(simId);
        simulation.ifPresent(simulationStatePresenter::simulationState);
    }

    @Override
    public void lookUpSimulationStates(SimulationStatePresenter simulationStatePresenter) {
        Optional<List<Simulation>> existingSimulations = simulationRepository.retrieveSimulations();
        if (existingSimulations.isEmpty())
            simulationStatePresenter.simulationState(Collections.emptyList());
        else {
            List<Simulation> simulationList = existingSimulations.get();
            simulationStatePresenter.simulationState(simulationList);
        }
    }

}
