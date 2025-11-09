package org.example.polyglotdataproyect.repositories;

import java.util.Optional;

import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerStatisticsRepository extends JpaRepository<TrainerStatistics, Long> {
    // Buscar estadísticas de un entrenador por mes y año
    Optional<TrainerStatistics> findByTrainerUsernameAndYearValueAndMonthValue(String trainerUsername, int year, int month);
}
