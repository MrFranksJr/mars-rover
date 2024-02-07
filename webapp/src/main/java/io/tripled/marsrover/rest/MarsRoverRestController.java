package io.tripled.marsrover.rest;


import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.SimulationState;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
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
    void landRover(@PathVariable int xCoordinate,@PathVariable int yCoordinate){
        Coordinate coordinate = new Coordinate(xCoordinate, yCoordinate);

        LandingPresenterImpl landingPresenter = new LandingPresenterImpl();
        marsRoverApi.landRover(coordinate, landingPresenter);
    }
    @PostMapping("/api/moverover/{roverName}/{roverMoves}")
    @ResponseBody
    void moveRover(@PathVariable String roverName,@PathVariable String roverMoves){

        final RoverMovePresenterImpl roverMovePresenter = new RoverMovePresenterImpl();
        marsRoverApi.moveRover(extractRoverMovesFromInput(roverMoves, roverName), roverMovePresenter);
    }

    private static List<RoverMove> extractRoverMovesFromInput(String input, String roverId) {
        List<RoverMove> roverMoves = new ArrayList<>();
        Pattern regex = Pattern.compile("( *[frblFRBL]\\d*)");
        Matcher matcher = regex.matcher(input);
        while(matcher.find()){
            String preppedInput = matcher.group(1).trim();
            String direction = preppedInput.substring(0,1);
            int steps = preppedInput.length() > 1 ? Integer.parseInt(preppedInput.substring(1)) : 1;
            final RoverMove roverMove = new RoverMove(roverId, direction, steps);
            roverMoves.add(roverMove);
        }

        return roverMoves;
    }


}
