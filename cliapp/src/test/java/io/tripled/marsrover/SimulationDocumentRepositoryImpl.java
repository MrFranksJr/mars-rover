package io.tripled.marsrover;

import io.tripled.marsrover.business.dbmodel.SimulationDocument;
import io.tripled.marsrover.business.domain.simulation.SimulationDocumentRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Repository
public class SimulationDocumentRepositoryImpl implements SimulationDocumentRepository {

    @Override
    public <S extends SimulationDocument> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends SimulationDocument> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends SimulationDocument> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends SimulationDocument, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> S save(S entity) {
        return null;
    }

    @Override
    public <S extends SimulationDocument> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<SimulationDocument> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<SimulationDocument> findAll() {
        return null;
    }

    @Override
    public List<SimulationDocument> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(SimulationDocument entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends SimulationDocument> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<SimulationDocument> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<SimulationDocument> findAll(Pageable pageable) {
        return null;
    }
}
