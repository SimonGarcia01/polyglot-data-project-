package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    // Buscar estadísticas de un usuario por mes y año
    Optional<UserStatistics> findByUsernameAndYearAndMonth(String username, int year, int month);
}
