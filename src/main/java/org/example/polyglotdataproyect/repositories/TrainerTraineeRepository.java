package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.TrainerTrainee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerTraineeRepository extends MongoRepository<TrainerTrainee, String> {

    List<TrainerTrainee> findByTrainerId(String trainerId);

    Optional<TrainerTrainee> findByTraineeId(String traineeId);

    List<TrainerTrainee> findByTrainerIdAndTraineeId(String trainerId, String traineeId);

    void deleteByTrainerIdAndTraineeId(String trainerId, String traineeId);
}
