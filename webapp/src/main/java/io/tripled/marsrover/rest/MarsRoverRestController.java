package io.tripled.marsrover.rest;

import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.dto.RoverLandingResultDTO;
import io.tripled.marsrover.dto.RoverMoveResultDTO;
import io.tripled.marsrover.dto.SimulationViewDTO;
import io.tripled.marsrover.presenters.LandingPresenterImpl;
import io.tripled.marsrover.presenters.RoverMovePresenterImpl;
import io.tripled.marsrover.presenters.SimulationCreationPresenterImpl;
import io.tripled.marsrover.presenters.SimulationStateRestPresenter;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import org.springframework.web.bind.annotation.*;

import static io.tripled.marsrover.rest.InputParser.INPUT_PARSER;
import static io.tripled.marsrover.rest.ResultParser.RESULT_PARSER;

@RestController
public class MarsRoverRestController {

    private final MarsRoverApi marsRoverApi;

    public MarsRoverRestController(MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
    }

    @PostMapping("/api/createsimulation/{simulationSize}")
    void createSimulation(@PathVariable int simulationSize) {
        SimulationCreationPresenterImpl simulationCreationPresenter = new SimulationCreationPresenterImpl();
        marsRoverApi.initializeSimulation(simulationSize, simulationCreationPresenter);
    }

    @GetMapping("/api/simulationstate")
    SimulationViewDTO getSimulationState() {
        SimulationStateRestPresenter simulationStatePresenter = new SimulationStateRestPresenter();

        marsRoverApi.lookUpSimulationState(simulationStatePresenter);

        return simulationStatePresenter.getSimulationState();
    }

    @PostMapping("/api/landrover/{xCoordinate}/{yCoordinate}")
    @ResponseBody
    RoverLandingResultDTO landRover(@PathVariable int xCoordinate, @PathVariable int yCoordinate) {
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);
        LandingPresenterImpl landingPresenter = new LandingPresenterImpl();

        marsRoverApi.landRover(coordinate, landingPresenter);
        //TODO: Not enough info about the landing. need to regain that intelligence
        return RESULT_PARSER.landExecutionResult(landingPresenter);
    }

    @PostMapping("/api/moverover/{roverInstructions}")
    @ResponseBody
    RoverMoveResultDTO moveRover(@PathVariable String roverInstructions) {
        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        final InstructionBatch roverInstructionsBatch = INPUT_PARSER.extractRoverMovesFromInput(roverInstructions);

        marsRoverApi.executeMoveInstructions(roverInstructionsBatch, roverMovePresenter);
        System.out.println("executing moveRover");
        return RESULT_PARSER.moveExecutionResult(roverInstructionsBatch, roverMovePresenter);
    }
}