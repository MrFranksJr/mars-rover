package io.tripled.marsrover.simulation;

public class InMemSimulationRepo implements SimulationRepository {

    private Simulation simWorld;

    @Override
    public void add(Simulation simulation) {
        if (simWorld == null){
            simWorld = simulation;
        }

    }

    @Override
    public Simulation getSimulation() {
        return simWorld;
    }
}
