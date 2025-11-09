package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoutineRepository extends MongoRepository<Routine, String> {

    List<Routine> findByOwnerId(String ownerId);

    List<Routine> findByIsTemplate(Boolean isTemplate);

    List<Routine> findByCopiedFrom(String copiedFrom);

    List<Routine> findByNameContainingIgnoreCase(String name);
}
