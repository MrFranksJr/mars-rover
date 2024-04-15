package io.tripled.marsrover.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.tripled.marsrover.MarsRoverApi;
import io.tripled.marsrover.api.simulation.SimulationSnapshot;
import io.tripled.marsrover.business.SimulationQuery;
import io.tripled.marsrover.business.SimulationRepository;
import io.tripled.marsrover.business.api.MarsRoverController;
import io.tripled.marsrover.vocabulary.SimulationId;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class SimulationNameStepDefinitions {
    private final SimulationRepository simulationRepository;
    private final MarsRoverApi marsRoverApi;
    private final SimulationQuery simulationQuery;
    private final HashMap<Integer, SimulationId> simulationMap = new HashMap<>();

    public SimulationNameStepDefinitions() {
        simulationRepository = new DummySimulationRepository();
        marsRoverApi = new MarsRoverController(simulationRepository);
        simulationQuery = (SimulationQuery) simulationRepository;
    }

    @When("We create a simulation with the name {string}")
    public void weCreateASimulationWithTheName(String simulationName) {
        marsRoverApi.initializeSimulation(simulationName, LoggingSimulationCreationPresenter.INSTANCE);
    }

    @Then("The system should show us that the Simulation has a size of {int}")
    public void theSystemShouldShowUsThatTheSimulationHasASizeOf(int simulationSize) {
        List<SimulationSnapshot> simulationSnapshots = simulationRepository.getSimulationSnapshots().orElseThrow();

        Assertions.assertEquals(simulationSize, simulationSnapshots.getFirst().simulationSize());
    }

    @And("The system should show us that the Simulation's name is {string}")
    public void theSystemShouldShowUsThatTheSimulationSNameIs(String simulationName) {
        List<SimulationSnapshot> simulationSnapshots = simulationRepository.getSimulationSnapshots().orElseThrow();

        Assertions.assertEquals(simulationName, simulationSnapshots.getFirst().simulationName());
    }
}
