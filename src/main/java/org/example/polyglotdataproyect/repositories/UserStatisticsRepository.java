package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.UserStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {

    Optional<UserStatistics> findByUserIdAndMonthAndYear(String userId, Integer month, Integer year);

    List<UserStatistics> findByUserId(String userId);

    List<UserStatistics> findByUserIdAndYear(String userId, Integer year);

    List<UserStatistics> findByMonthAndYear(Integer month, Integer year);
}
