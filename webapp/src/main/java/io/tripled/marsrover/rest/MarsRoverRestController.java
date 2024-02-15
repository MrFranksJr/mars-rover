package io.tripled.marsrover.rest;


import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.vocabulary.InstructionBatch;
import io.tripled.marsrover.vocabulary.RoverMove;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MarsRoverRestController {

    private final MarsRoverApi marsRoverApi;

    public MarsRoverRestController(MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
    }

    private static String buildRegularExpression(String[] inputArray) {
        return "^" + "( *[FfRrBbLl]\\d*)"
                .repeat(inputArray.length) +
                "$|^( *[FfRrBbLl]\\d*)$";
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
    String landRover(@PathVariable int xCoordinate, @PathVariable int yCoordinate) {
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);

        LandingPresenterImpl landingPresenter = new LandingPresenterImpl();
        marsRoverApi.landRover(coordinate, landingPresenter);
        SimulationStateRestPresenter simulationStatePresenter = new SimulationStateRestPresenter();

        marsRoverApi.lookUpSimulationState(simulationStatePresenter);
        int simulationSize = simulationStatePresenter.getSimulationState().simulationSize();
        if (xCoordinate <= simulationSize && yCoordinate <= simulationSize) {
            return "{\"result\":\"Landing successful\"}";
        } else {
            return "{\"result\":\"Landing unsuccessful\"}";
        }
    }

    @PostMapping("/api/moverover/{roverInstructions}")
    @ResponseBody
    String moveRover(@PathVariable String roverInstructions) {
        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        final InstructionBatch roverInstructionsBatch = extractRoverMovesFromInput(roverInstructions);

        marsRoverApi.executeMoveInstructions(roverInstructionsBatch, roverMovePresenter);

        if (!roverInstructionsBatch.batch().isEmpty() && !roverMovePresenter.hasCollided().second()) {
            return "{\"result\":\"Rover moves successful\"}";
        } else if(!roverInstructionsBatch.batch().isEmpty() && roverMovePresenter.hasCollided().second()) {
            String roverId = roverMovePresenter.hasCollided().first().id();
            return "{\"result\":\"Rover " + roverId + " has collided\"}";

        }else {
            return "{\"result\":\"Rover moves unsuccessful\"}";
        }
    }

    private static InstructionBatch extractRoverMovesFromInput(String roverMoves) {
        final InstructionBatch.Builder instructionBatch = InstructionBatch.newBuilder();
        roverMoves.replace("%20", " ");
        String[] roverInstructions = roverMoves.split("\\s+(?=[R])");

        for (String instructionsPerRover : roverInstructions) {
            String roverId = extractRoverIdFromInput(instructionsPerRover);

            Pattern regex = Pattern.compile("( [frbl]\\d*)");
            Matcher matcher = regex.matcher(instructionsPerRover);
            while(matcher.find()){
                String preppedInput = matcher.group(1).trim();
                String direction = preppedInput.substring(0,1);
                int steps = preppedInput.length() > 1 ? Integer.parseInt(preppedInput.substring(1)) : 1;
                instructionBatch.addRoverMoves(roverId, new RoverMove(direction, steps));
            }
        }
        return instructionBatch.build();
    }

    private static String extractRoverIdFromInput(String input) {
        return input.substring(0, input.indexOf(" ")).toUpperCase();
    }
}