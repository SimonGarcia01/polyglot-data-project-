package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Exercise;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends MongoRepository<Exercise, String> {

    List<Exercise> findByType(String type);

    List<Exercise> findByDifficulty(String difficulty);

    List<Exercise> findByCreatedBy(String createdBy);

    List<Exercise> findByNameContainingIgnoreCase(String name);
}
