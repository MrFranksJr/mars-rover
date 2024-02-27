package io.tripled.marsrover.business.dbmodel;

import com.google.common.collect.Multimap;
import io.tripled.marsrover.business.domain.rover.Location;
import io.tripled.marsrover.business.domain.rover.Rover;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Document("simulationrepository")
public class SimulationDocument {

    @Id
    private final UUID id;

    private final int simulationSize;
    private int nrOfRovers;
    private final Multimap<Location, Rover> roverLocationMap;

    public SimulationDocument(UUID id, int simulationSize, Multimap<Location, Rover> roverLocationMap) {
        super();
        this.id = id;
        this.simulationSize = simulationSize;
        this.roverLocationMap = roverLocationMap;
        this.nrOfRovers = roverLocationMap.size();
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

    public Multimap<Location, Rover> getRoverLocationMap() {
        return roverLocationMap;
    }
}
