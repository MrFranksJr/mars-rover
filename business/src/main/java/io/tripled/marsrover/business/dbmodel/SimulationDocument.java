package io.tripled.marsrover.business.dbmodel;

import com.google.common.collect.Multimap;
import io.tripled.marsrover.business.api.RoverState;
import io.tripled.marsrover.business.api.SimulationSnapshot;
import io.tripled.marsrover.business.domain.rover.Location;
import io.tripled.marsrover.business.domain.rover.Rover;
import io.tripled.marsrover.business.domain.simulation.Simulation;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document("simulationrepository")
public class SimulationDocument {

    @Id
    private final UUID id;

    private final int simulationSize;
    private int nrOfRovers;
    private final List<RoverState> roverList;

    public SimulationDocument(UUID id, int simulationSize, List<RoverState> roverList) {
        super();
        this.id = id;
        this.simulationSize = simulationSize;
        this.roverList = roverList;
        this.nrOfRovers = roverList.size();
    }

    public SimulationDocument(Simulation simulation){
        super();
        SimulationSnapshot snapshot = simulation.takeSnapshot();
        this.id = snapshot.id();
        this.simulationSize = snapshot.simulationSize();
        this.roverList = snapshot.roverList();
        this.nrOfRovers = roverList.size();
    }

    public UUID getId() {
        return id;
    }

    public int getSimulationSize() {
        return simulationSize;
    }

    public int getNrOfRovers() {
        return nrOfRovers;
    }

    public void setNrOfRovers(int nrOfRovers) {
        this.nrOfRovers = nrOfRovers;
    }

    public List<RoverState> getRoverList() {
        return roverList;
    }
}
