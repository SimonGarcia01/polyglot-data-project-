package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerStatisticsRepository extends JpaRepository<TrainerStatistics, Long> {
    // Buscar estadísticas de un entrenador por mes y año
    Optional<TrainerStatistics> findByTrainerUsernameAndYearAndMonth(String trainerUsername, int year, int month);
}
