package io.tripled.marsrover.business.dbmodel;

import com.google.common.collect.Multimap;
import io.tripled.marsrover.business.domain.rover.Location;
import io.tripled.marsrover.business.domain.rover.Rover;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("simulationrepository")
public class SimulationDocument {

    @Id
    private int id;

    private int simulationSize;
    private int nrOfRovers;
    private final Multimap<Location, Rover> roverLocationMap;

}
