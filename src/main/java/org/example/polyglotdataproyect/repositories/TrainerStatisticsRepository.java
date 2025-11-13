package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerStatisticsRepository extends JpaRepository<TrainerStatistics, Long> {

    Optional<TrainerStatistics> findByTrainerIdAndMonthAndYear(String trainerId, Integer month, Integer year);

    List<TrainerStatistics> findByTrainerId(String trainerId);

    List<TrainerStatistics> findByTrainerIdAndYear(String trainerId, Integer year);

    List<TrainerStatistics> findByMonthAndYear(Integer month, Integer year);
}
