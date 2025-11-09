package org.example.polyglotdataproyect.repositories;

import java.util.Optional;

import org.example.polyglotdataproyect.entities.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    // Buscar estadísticas de un usuario por mes y año
    Optional<UserStatistics> findByUsernameAndYearValueAndMonthValue(String username, int year, int month);
}
