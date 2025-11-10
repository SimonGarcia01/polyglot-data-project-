package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.Exercise;
import org.example.polyglotdataproyect.entities.ProgressEntry;
import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.repositories.ExerciseRepository;
import org.example.polyglotdataproyect.repositories.ProgressEntryRepository;
import org.example.polyglotdataproyect.repositories.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ProgressEntryRepository progressEntryRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private RoutineRepository routineRepository;

    /**
     * INFORME 1: Activity Heatmap
     * Genera un mapa de actividad de los últimos 365 días para un usuario
     * Retorna un mapa de fechas con el conteo de actividades
     */
    public Map<String, Integer> getActivityHeatmap(String userId) {
        List<ProgressEntry> allProgress = progressEntryRepository.findByUserId(userId);

        Map<String, Integer> heatmapData = new TreeMap<>();

        // Inicializar los últimos 365 días con 0
        LocalDate today = LocalDate.now();
        for (int i = 0; i < 365; i++) {
            LocalDate date = today.minusDays(i);
            heatmapData.put(date.toString(), 0);
        }

        // Contar actividades por día
        for (ProgressEntry progressEntry : allProgress) {
            if (progressEntry.getEntries() != null) {
                for (ProgressEntry.Entry entry : progressEntry.getEntries()) {
                    if (entry.getDate() != null) {
                        LocalDate entryDate = entry.getDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();

                        String dateKey = entryDate.toString();
                        if (heatmapData.containsKey(dateKey)) {
                            heatmapData.put(dateKey, heatmapData.get(dateKey) + 1);
                        }
                    }
                }
            }
        }

        return heatmapData;
    }

    /**
     * INFORME 2: Progress by Exercise Type
     * Analiza el progreso del usuario agrupado por tipo de ejercicio (cardio, fuerza, movilidad)
     * Retorna cantidad de sesiones y esfuerzo promedio por tipo
     */
    public Map<String, ExerciseTypeStats> getProgressByExerciseType(String userId) {
        List<ProgressEntry> allProgress = progressEntryRepository.findByUserId(userId);

        // Mapa para acumular estadísticas por tipo
        Map<String, ExerciseTypeStats> typeStatsMap = new HashMap<>();
        typeStatsMap.put("cardio", new ExerciseTypeStats("cardio"));
        typeStatsMap.put("fuerza", new ExerciseTypeStats("fuerza"));
        typeStatsMap.put("movilidad", new ExerciseTypeStats("movilidad"));

        for (ProgressEntry progressEntry : allProgress) {
            // Obtener la rutina para saber qué ejercicios tiene
            Optional<Routine> routineOpt = routineRepository.findById(progressEntry.getRoutineId());
            if (routineOpt.isEmpty()) continue;

            Routine routine = routineOpt.get();

            // Contar tipos de ejercicios en esta rutina
            Map<String, Integer> typesInRoutine = new HashMap<>();
            typesInRoutine.put("cardio", 0);
            typesInRoutine.put("fuerza", 0);
            typesInRoutine.put("movilidad", 0);

            for (Routine.ExerciseInRoutine exerciseInRoutine : routine.getExercises()) {
                Optional<Exercise> exerciseOpt = exerciseRepository.findById(exerciseInRoutine.getExerciseId());
                if (exerciseOpt.isPresent()) {
                    String type = exerciseOpt.get().getType();
                    typesInRoutine.put(type, typesInRoutine.getOrDefault(type, 0) + 1);
                }
            }

            // Determinar el tipo predominante de la rutina
            String predominantType = typesInRoutine.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("fuerza");

            // Agregar estadísticas de cada entry
            if (progressEntry.getEntries() != null) {
                for (ProgressEntry.Entry entry : progressEntry.getEntries()) {
                    ExerciseTypeStats stats = typeStatsMap.get(predominantType);
                    stats.addSession(entry.getEffortLevel());
                }
            }
        }

        return typeStatsMap;
    }

    /**
     * INFORME 3: Consistency Score
     * Calcula un score de consistencia del usuario basado en:
     * - Frecuencia de entrenamientos
     * - Racha actual
     * - Total de sesiones
     */
    public ConsistencyReport getConsistencyReport(String userId) {
        List<ProgressEntry> allProgress = progressEntryRepository.findByUserId(userId);

        // Recolectar todas las fechas de entrenamiento
        Set<LocalDate> workoutDates = new TreeSet<>();
        int totalSessions = 0;
        int totalEffort = 0;

        for (ProgressEntry progressEntry : allProgress) {
            if (progressEntry.getEntries() != null) {
                for (ProgressEntry.Entry entry : progressEntry.getEntries()) {
                    if (entry.getDate() != null) {
                        LocalDate date = entry.getDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                        workoutDates.add(date);
                        totalSessions++;
                        totalEffort += entry.getEffortLevel();
                    }
                }
            }
        }

        // Calcular racha actual
        int currentStreak = calculateCurrentStreak(workoutDates);

        // Calcular racha más larga
        int longestStreak = calculateLongestStreak(workoutDates);

        // Calcular promedio de esfuerzo
        double averageEffort = totalSessions > 0 ? (double) totalEffort / totalSessions : 0.0;

        // Calcular días activos en los últimos 30 días
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        long activeDaysLast30 = workoutDates.stream()
            .filter(date -> date.isAfter(thirtyDaysAgo))
            .count();

        return new ConsistencyReport(
            totalSessions,
            workoutDates.size(),
            currentStreak,
            longestStreak,
            averageEffort,
            (int) activeDaysLast30
        );
    }

    /**
     * INFORME 4: Most Popular Template Routines
     * Rutinas template más copiadas por los usuarios
     */
    public List<RoutinePopularity> getMostPopularTemplates() {
        List<Routine> allRoutines = routineRepository.findAll();

        // Contar cuántas veces se copió cada template
        Map<String, RoutinePopularity> popularityMap = new HashMap<>();

        for (Routine routine : allRoutines) {
            if (routine.getCopiedFrom() != null && !routine.getCopiedFrom().isEmpty()) {
                String templateId = routine.getCopiedFrom();

                RoutinePopularity popularity = popularityMap.computeIfAbsent(
                    templateId,
                    id -> {
                        Optional<Routine> templateOpt = routineRepository.findById(id);
                        return new RoutinePopularity(
                            id,
                            templateOpt.map(Routine::getName).orElse("Unknown"),
                            0
                        );
                    }
                );
                popularity.incrementCopies();
            }
        }

        // Ordenar por cantidad de copias descendente
        return popularityMap.values().stream()
            .sorted((a, b) -> Integer.compare(b.getCopyCount(), a.getCopyCount()))
            .collect(Collectors.toList());
    }

    // Métodos auxiliares

    private int calculateCurrentStreak(Set<LocalDate> workoutDates) {
        if (workoutDates.isEmpty()) return 0;

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        // Si no entrenó hoy ni ayer, racha = 0
        if (!workoutDates.contains(today) && !workoutDates.contains(yesterday)) {
            return 0;
        }

        int streak = 0;
        LocalDate currentDate = workoutDates.contains(today) ? today : yesterday;

        while (workoutDates.contains(currentDate)) {
            streak++;
            currentDate = currentDate.minusDays(1);
        }

        return streak;
    }

    private int calculateLongestStreak(Set<LocalDate> workoutDates) {
        if (workoutDates.isEmpty()) return 0;

        List<LocalDate> sortedDates = new ArrayList<>(workoutDates);
        Collections.sort(sortedDates);

        int longestStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < sortedDates.size(); i++) {
            LocalDate prev = sortedDates.get(i - 1);
            LocalDate curr = sortedDates.get(i);

            if (prev.plusDays(1).equals(curr)) {
                currentStreak++;
                longestStreak = Math.max(longestStreak, currentStreak);
            } else {
                currentStreak = 1;
            }
        }

        return longestStreak;
    }

    // Clases internas para DTOs

    public static class ExerciseTypeStats {
        private String type;
        private int sessionCount;
        private int totalEffort;

        public ExerciseTypeStats(String type) {
            this.type = type;
            this.sessionCount = 0;
            this.totalEffort = 0;
        }

        public void addSession(int effortLevel) {
            this.sessionCount++;
            this.totalEffort += effortLevel;
        }

        public String getType() { return type; }
        public int getSessionCount() { return sessionCount; }
        public double getAverageEffort() {
            return sessionCount > 0 ? (double) totalEffort / sessionCount : 0.0;
        }
    }

    public static class ConsistencyReport {
        private int totalSessions;
        private int uniqueDays;
        private int currentStreak;
        private int longestStreak;
        private double averageEffort;
        private int activeDaysLast30;

        public ConsistencyReport(int totalSessions, int uniqueDays, int currentStreak,
                               int longestStreak, double averageEffort, int activeDaysLast30) {
            this.totalSessions = totalSessions;
            this.uniqueDays = uniqueDays;
            this.currentStreak = currentStreak;
            this.longestStreak = longestStreak;
            this.averageEffort = averageEffort;
            this.activeDaysLast30 = activeDaysLast30;
        }

        // Getters
        public int getTotalSessions() { return totalSessions; }
        public int getUniqueDays() { return uniqueDays; }
        public int getCurrentStreak() { return currentStreak; }
        public int getLongestStreak() { return longestStreak; }
        public double getAverageEffort() { return averageEffort; }
        public int getActiveDaysLast30() { return activeDaysLast30; }

        public int getConsistencyScore() {
            // Score de 0-100 basado en actividad reciente
            double score = (activeDaysLast30 / 30.0) * 100;
            return (int) Math.min(100, score);
        }
    }

    public static class RoutinePopularity {
        private String routineId;
        private String routineName;
        private int copyCount;

        public RoutinePopularity(String routineId, String routineName, int copyCount) {
            this.routineId = routineId;
            this.routineName = routineName;
            this.copyCount = copyCount;
        }

        public void incrementCopies() {
            this.copyCount++;
        }

        // Getters
        public String getRoutineId() { return routineId; }
        public String getRoutineName() { return routineName; }
        public int getCopyCount() { return copyCount; }
    }
}
