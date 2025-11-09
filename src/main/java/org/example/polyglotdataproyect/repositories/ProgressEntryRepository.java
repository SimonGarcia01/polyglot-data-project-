package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.ProgressEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgressEntryRepository extends MongoRepository<ProgressEntry, String> {

    List<ProgressEntry> findByUserId(String userId);

    List<ProgressEntry> findByRoutineId(String routineId);

    Optional<ProgressEntry> findByUserIdAndRoutineId(String userId, String routineId);
}
