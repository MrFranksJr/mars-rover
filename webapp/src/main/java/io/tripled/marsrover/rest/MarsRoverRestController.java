package io.tripled.marsrover.rest;


import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class MarsRoverRestController {

    private final MarsRoverApi marsRoverApi;

    public MarsRoverRestController(MarsRoverApi marsRoverApi) {
        this.marsRoverApi = marsRoverApi;
    }

    @PostMapping("/api/createsimulation/{simulationSize}")
    void createSimulation(@PathVariable int simulationSize){
        SimulationCreationPresenterImpl simulationCreationPresenter = new SimulationCreationPresenterImpl();
        marsRoverApi.initializeSimulation(simulationSize, simulationCreationPresenter);
    }

    @GetMapping("/api/simulationstate")
    SimulationState getSimulationState(){
        SimulationStatePresenterImpl simulationStatePresenter = new SimulationStatePresenterImpl();

        marsRoverApi.lookUpSimulationState(simulationStatePresenter);

        return simulationStatePresenter.getSimulationState();
    }

    @PostMapping("/api/landrover/{xCoordinate}/{yCoordinate}")
    @ResponseBody
    String landRover(@PathVariable int xCoordinate,@PathVariable int yCoordinate){
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);

        LandingPresenterImpl landingPresenter = new LandingPresenterImpl();
        marsRoverApi.landRover(coordinate, landingPresenter);
        SimulationStatePresenterImpl simulationStatePresenter = new SimulationStatePresenterImpl();

        marsRoverApi.lookUpSimulationState(simulationStatePresenter);
        int simulationSize = simulationStatePresenter.getSimulationState().simulationSize();
        if(xCoordinate <= simulationSize && yCoordinate <= simulationSize){
            return "{\"result\":\"Landing successful\"}";
        } else {
            return "{\"result\":\"Landing unsuccessful\"}";
        }
    }
    @PostMapping("/api/moverover/{roverName}/{roverMoves}")
    @ResponseBody
    String moveRover(@PathVariable String roverName,@PathVariable String roverMoves){

        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        List<RoverMove> roverMovesFromString = extractRoverMovesFromInput(roverMoves, roverName);
        marsRoverApi.moveRover(roverMovesFromString, roverMovePresenter);
        if(roverMovesFromString.isEmpty())
            return roverMovePresenter.moveRoverUnsuccesful();

        return "Rover has successfully moved";
    }

    private static List<RoverMove> extractRoverMovesFromInput(String input, String roverId) {
        System.out.println("move rover triggered. Input: " + input + " and RoverID: " + roverId);
        String[] inputArray = input.split(" ");
        List<RoverMove> roverMoves = new ArrayList<>();

        for (String section : inputArray) {
            Pattern regex = Pattern.compile("[FfRrBbLl]\\d*[1-9]?");
            Matcher matcher = regex.matcher(section);

            if (matcher.find()) {
                String preppedInput = matcher.group();
                String direction = preppedInput.substring(0, 1);
                int steps = preppedInput.length() > 1 ? Integer.parseInt(preppedInput.substring(1)) : 1;
                final RoverMove roverMove = new RoverMove(roverId, direction, steps);
                roverMoves.add(roverMove);
            } else return Collections.emptyList();
        }
        return roverMoves;
    }
}
