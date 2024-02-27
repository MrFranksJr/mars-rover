package io.tripled.marsrover.business.domain.simulation;

import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SimulationDocumentRepositoryImpl implements  SimulationDocumentRepository{
    @Override
    public void add(Simulation simulation) {

    }

    @Override
    public void save(Simulation simulation) {

    }

    @Override
    public Optional<Simulation> getSimulation(int simulationId) {
        return Optional.empty();
    }

}
