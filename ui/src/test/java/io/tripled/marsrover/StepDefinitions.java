package io.tripled.marsrover;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.business.api.MarsRoverApi;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.business.domain.rover.Coordinate;
import io.tripled.marsrover.business.domain.rover.RoverMove;
import io.tripled.marsrover.business.domain.simulation.InMemSimulationRepo;
import io.tripled.marsrover.business.domain.simulation.SimulationRepository;
import io.tripled.marsrover.cli.commands.Command;
import io.tripled.marsrover.cli.commands.LandCommand;
import io.tripled.marsrover.cli.commands.RoverMoveCommand;
import io.tripled.marsrover.cli.commands.SimSetupCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepDefinitions {
    private final MarsRoverApi marsRoverApi;
    private final SimulationRepository simulationRepository;
    private final DummyPresenter dummyPresenter;
public StepDefinitions() {
        dummyPresenter = new DummyPresenter();
        simulationRepository = new InMemSimulationRepo();
        marsRoverApi = new MarsRoverController(simulationRepository);
    }

    @Given("A simulation of size {int}")
    public void aSimulationOfSizeSimSize(int simulationSize) {
        SimSetupCommand simSetupCommand = new SimSetupCommand(simulationSize,marsRoverApi);
        simSetupCommand.execute(dummyPresenter);
    }

    @And("We land a rover on coordinates {int} {int}")
    public void weLandARoverOnCoordinatesXY(int x, int y) {
        LandCommand landCommand = new LandCommand(new Coordinate(x, y), marsRoverApi);
        landCommand.execute(dummyPresenter);
    }

    @When("We give the Rover {string} the Instruction {string} {int}")
    public void weGiveTheRoverTheInstructionAmount(String roverName, String instruction, int steps) {
        String directionString = instruction.substring(0, 1);
        Command roverMoveCommand = new RoverMoveCommand(List.of(new RoverMove(roverName, directionString, steps)), marsRoverApi);

        roverMoveCommand.execute(dummyPresenter);
    }

    @When("We give the Rover {string} the Instructions")
    public void weGiveTheRoverTheInstructions(String roverName, io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> instructions = dataTable.asMaps(String.class, String.class);

        List<RoverMove> roverMovesList = new ArrayList<>();

        for (Map<String, String> row : instructions) {
            String command = row.get("instruction").substring(0, 1);
            int amount = Integer.parseInt(row.get("amount"));

            roverMovesList.add(new RoverMove(roverName, command, amount));
        }

        Command roverMoveCommand = new RoverMoveCommand(roverMovesList, marsRoverApi);

        roverMoveCommand.execute(dummyPresenter);
    }


    @Then("The Rover {string} is at {int} {int} with orientation {string}")
    public void theRoverIsAtNewXNewYWithOrientation(String roverName, int newX, int newY, String heading) {
        assertEquals(roverName, simulationRepository.getSimulation().getRoverList().getFirst().getRoverName());
        assertEquals(newX, simulationRepository.getSimulation().getRoverList().getFirst().getRoverXPosition());
        assertEquals(newY, simulationRepository.getSimulation().getRoverList().getFirst().getRoverYPosition());
        assertEquals(heading, simulationRepository.getSimulation().getRoverList().getFirst().getRoverHeading().toString().toLowerCase());
    }

    @Then("No rover should be present in the simulation")
    public void no_rover_should_be_present_in_the_simulation() {
        assertEquals(0, simulationRepository.getSimulation().getRoverList().size());
    }
}
