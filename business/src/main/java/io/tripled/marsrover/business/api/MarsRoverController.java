package io.tripled.marsrover.business.api;

import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.simulation.*;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverInstructions;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MarsRoverController implements MarsRoverApi {
    private final SimulationRepository simulationRepository;
    private final SimulationDocumentRepository simulationDocumentRepository;
    private final int simulationId = 1;

    public MarsRoverController(SimulationRepository simulationRepository, SimulationDocumentRepository simulationDocumentRepository) {
        this.simulationRepository = simulationRepository;
        this.simulationDocumentRepository = simulationDocumentRepository;
    }

    private static Simulation.SimulationLandingEventPublisher createEventPublisher(LandingPresenter landingPresenter) {
        return event -> presentLanding(landingPresenter, event);
    }

    private static void presentLanding(LandingPresenter p, Simulation.SimulationLandEvent e) {
        switch (e) {
            case Simulation.LandingSuccessfulLandEvent l -> p.landingSuccessfulRefactor(l.roverState());
            case Simulation.RoverMissesSimulationLand r -> p.roverMissesSimulation(r.simulationSize(), r.coordinate());
            case Simulation.InvalidCoordinatesReceived i -> p.negativeCoordinatesReceived(i.coordinate());
            case Simulation.LandingOnTopEvent l -> p.landingOnTop(l);
        }
    }

    private static void presentRoverMoved(RoverMovePresenter p, Simulation.SimulationMoveRoverEvent e) {
        switch (e) {
            case Simulation.RoverMovedSuccessfulEvent r -> p.moveRoverSuccessful(r.roverState());
            case Simulation.RoverCollided roverCollided -> p.roverCollided(roverCollided.roverState());
            case Simulation.RoverDeath roverDeath -> p.roverBreakingDown(roverDeath.roverState());
            case Simulation.RoverAlreadyBroken roverAlreadyBroken -> p.roverAlreadyBrokenDown(roverAlreadyBroken.roverId());
        }
    }

    @Override
    public void landRover(Coordinate coordinate, LandingPresenter landingPresenter) {
        Optional<Simulation> simulation = simulationRepository.getSimulation(simulationId);
        if (simulation.isPresent()) {
            final var eventPublisher = createEventPublisher(landingPresenter);

            simulation.get().landRover(coordinate, eventPublisher);

            simulationRepository.save(simulation.get());
            simulationDocumentRepository.save(new SimulationDocument(simulation.get()));
        }
    }

    @Override
    public void executeMoveInstructions(InstructionBatch instructionBatch, RoverMovePresenter roverMovePresenter) {
        Optional<Simulation> simulation = simulationRepository.getSimulation(simulationId);
        if (simulation.isPresent()) {
            for (RoverInstructions roverInstructions : instructionBatch.batch()) {
                simulation.get().moveRover(roverInstructions, event -> presentRoverMoved(roverMovePresenter, event));
            }
            simulationRepository.save(simulation.get());
            simulationDocumentRepository.save(new SimulationDocument(simulation.get()));

            //TODO:simultaneous rover move cleanup
            //simulation.moveRovers(instructionBatch.batch());
        }
    }

    @Override
    public void initializeSimulation(int simulationSize, SimulationCreationPresenter simulationCreationPresenter) {
        //TODO: unlock ability to create multiple simulations
        final Optional<Simulation> simulation = Simulation.create(simulationSize);
        if (simulation.isEmpty())
            simulationCreationPresenter.simulationCreationUnsuccessful(simulationSize);
        else {
            final var simWorld = simulation.get();
            simulationRepository.add(simWorld);
            simulationDocumentRepository.save(new SimulationDocument(simulation.get()));
            simulationCreationPresenter.simulationCreationSuccessful(simWorld.takeSnapshot());
        }
    }

    @Override
    public void lookUpSimulationState(SimulationStatePresenter simulationStatePresenter) {
        Optional<Simulation> simulation = simulationRepository.getSimulation(simulationId);
        if (simulation.isPresent()) {
            simulationStatePresenter.simulationState(simulation.get().takeSnapshot());
        }
        else
            simulationStatePresenter.simulationState(SimulationSnapshot.NONE);
    }
}
