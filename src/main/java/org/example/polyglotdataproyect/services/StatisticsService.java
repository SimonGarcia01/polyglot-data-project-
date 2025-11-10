package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.example.polyglotdataproyect.entities.UserStatistics;
import org.example.polyglotdataproyect.repositories.TrainerStatisticsRepository;
import org.example.polyglotdataproyect.repositories.UserStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StatisticsService {

    @Autowired
    private UserStatisticsRepository userStatisticsRepository;

    @Autowired
    private TrainerStatisticsRepository trainerStatisticsRepository;

    /**
     * Incrementa el contador de rutinas iniciadas para un usuario en el mes actual
     */
    @Transactional
    public void incrementRoutineStarted(String userId) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        UserStatistics stats = getOrCreateUserStatistics(userId, month, year);
        stats.setRoutinesStarted(stats.getRoutinesStarted() + 1);
        userStatisticsRepository.save(stats);
    }

    /**
     * Incrementa el contador de seguimientos (progress logs) para un usuario en el mes actual
     */
    @Transactional
    public void incrementProgressLog(String userId) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        UserStatistics stats = getOrCreateUserStatistics(userId, month, year);
        stats.setProgressLogsCount(stats.getProgressLogsCount() + 1);
        userStatisticsRepository.save(stats);
    }

    /**
     * Incrementa el contador de nuevas asignaciones para un entrenador en el mes actual
     */
    @Transactional
    public void incrementNewAssignment(String trainerId) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        TrainerStatistics stats = getOrCreateTrainerStatistics(trainerId, month, year);
        stats.setNewAssignmentsCount(stats.getNewAssignmentsCount() + 1);
        trainerStatisticsRepository.save(stats);
    }

    /**
     * Incrementa el contador de feedbacks dados por un entrenador en el mes actual
     */
    @Transactional
    public void incrementFeedbackGiven(String trainerId) {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        TrainerStatistics stats = getOrCreateTrainerStatistics(trainerId, month, year);
        stats.setFeedbacksGivenCount(stats.getFeedbacksGivenCount() + 1);
        trainerStatisticsRepository.save(stats);
    }

    /**
     * Obtiene o crea estadísticas para un usuario en un mes/año específico
     */
    private UserStatistics getOrCreateUserStatistics(String userId, int month, int year) {
        Optional<UserStatistics> existing = userStatisticsRepository.findByUserIdAndMonthAndYear(userId, month, year);

        if (existing.isPresent()) {
            return existing.get();
        }

        UserStatistics newStats = new UserStatistics();
        newStats.setUserId(userId);
        newStats.setMonth(month);
        newStats.setYear(year);
        newStats.setRoutinesStarted(0);
        newStats.setProgressLogsCount(0);
        newStats.setCreatedAt(LocalDateTime.now());
        newStats.setUpdatedAt(LocalDateTime.now());

        return newStats;
    }

    /**
     * Obtiene o crea estadísticas para un entrenador en un mes/año específico
     */
    private TrainerStatistics getOrCreateTrainerStatistics(String trainerId, int month, int year) {
        Optional<TrainerStatistics> existing = trainerStatisticsRepository.findByTrainerIdAndMonthAndYear(trainerId, month, year);

        if (existing.isPresent()) {
            return existing.get();
        }

        TrainerStatistics newStats = new TrainerStatistics();
        newStats.setTrainerId(trainerId);
        newStats.setMonth(month);
        newStats.setYear(year);
        newStats.setNewAssignmentsCount(0);
        newStats.setFeedbacksGivenCount(0);
        newStats.setCreatedAt(LocalDateTime.now());
        newStats.setUpdatedAt(LocalDateTime.now());

        return newStats;
    }

    // ==================== MÉTODOS DE CONSULTA ====================

    /**
     * Obtiene las estadísticas de un usuario para todos los meses
     */
    public List<UserStatistics> getUserStatistics(String userId) {
        return userStatisticsRepository.findByUserId(userId);
    }

    /**
     * Obtiene las estadísticas de un usuario para un año específico
     */
    public List<UserStatistics> getUserStatisticsByYear(String userId, int year) {
        return userStatisticsRepository.findByUserIdAndYear(userId, year);
    }

    /**
     * Obtiene las estadísticas de un usuario para un mes/año específico
     */
    public Optional<UserStatistics> getUserStatisticsForMonth(String userId, int month, int year) {
        return userStatisticsRepository.findByUserIdAndMonthAndYear(userId, month, year);
    }

    /**
     * Obtiene las estadísticas de todos los usuarios para un mes/año específico
     */
    public List<UserStatistics> getAllUserStatisticsForMonth(int month, int year) {
        return userStatisticsRepository.findByMonthAndYear(month, year);
    }

    /**
     * Obtiene las estadísticas de un entrenador para todos los meses
     */
    public List<TrainerStatistics> getTrainerStatistics(String trainerId) {
        return trainerStatisticsRepository.findByTrainerId(trainerId);
    }

    /**
     * Obtiene las estadísticas de un entrenador para un año específico
     */
    public List<TrainerStatistics> getTrainerStatisticsByYear(String trainerId, int year) {
        return trainerStatisticsRepository.findByTrainerIdAndYear(trainerId, year);
    }

    /**
     * Obtiene las estadísticas de un entrenador para un mes/año específico
     */
    public Optional<TrainerStatistics> getTrainerStatisticsForMonth(String trainerId, int month, int year) {
        return trainerStatisticsRepository.findByTrainerIdAndMonthAndYear(trainerId, month, year);
    }

    /**
     * Obtiene las estadísticas de todos los entrenadores para un mes/año específico
     */
    public List<TrainerStatistics> getAllTrainerStatisticsForMonth(int month, int year) {
        return trainerStatisticsRepository.findByMonthAndYear(month, year);
    }
}
