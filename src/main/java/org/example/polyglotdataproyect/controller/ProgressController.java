package org.example.polyglotdataproyect.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.polyglotdataproyect.dto.ProgressDto;
import org.example.polyglotdataproyect.entities.Progress;
import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.entities.UserStatistics;
import org.example.polyglotdataproyect.repositories.ProgressRepository;
import org.example.polyglotdataproyect.repositories.RoutineRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.example.polyglotdataproyect.repositories.UserStatisticsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private final ProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    public ProgressController(ProgressRepository progressRepository, UserRepository userRepository,
                            RoutineRepository routineRepository, UserStatisticsRepository userStatisticsRepository) {
        this.progressRepository = progressRepository;
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
        this.userStatisticsRepository = userStatisticsRepository;
    }

    // Registrar progreso
    @PostMapping("/{username}")
    public ResponseEntity<Progress> recordProgress(@PathVariable String username, @RequestBody ProgressDto progressDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Optional<Routine> optionalRoutine = routineRepository.findById(progressDto.getRoutineId());
        if (optionalRoutine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Progress progress = new Progress();
        progress.setUsername(username);
        progress.setRoutineId(progressDto.getRoutineId());
        progress.setProgressDate(progressDto.getProgressDate());
        progress.setMetrics(progressDto.getMetrics());
        progress.setNotes(progressDto.getNotes());

        Progress savedProgress = progressRepository.save(progress);

        // Actualizar estadísticas
        updateUserStatistics(username);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProgress);
    }

    // Obtener progreso de un usuario
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Progress>> getUserProgress(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Progress> progressList = progressRepository.findByUsername(username);
        return ResponseEntity.ok(progressList);
    }

    // Obtener progreso de una rutina
    @GetMapping("/routine/{routineId}")
    public ResponseEntity<List<Progress>> getRoutineProgress(@PathVariable String routineId) {
        Optional<Routine> optionalRoutine = routineRepository.findById(routineId);
        if (optionalRoutine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Progress> progressList = progressRepository.findByRoutineId(routineId);
        return ResponseEntity.ok(progressList);
    }

    // Obtener progreso de un usuario en una rutina específica
    @GetMapping("/user/{username}/routine/{routineId}")
    public ResponseEntity<List<Progress>> getUserRoutineProgress(@PathVariable String username, @PathVariable String routineId) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Progress> progressList = progressRepository.findByUsernameAndRoutineId(username, routineId);
        return ResponseEntity.ok(progressList);
    }

    // Método auxiliar para actualizar estadísticas
    private void updateUserStatistics(String username) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        Optional<UserStatistics> statsOpt = userStatisticsRepository.findByUsernameAndYearValueAndMonthValue(username, year, month);
        UserStatistics stats;

        if (statsOpt.isPresent()) {
            stats = statsOpt.get();
        } else {
            stats = new UserStatistics();
            stats.setUsername(username);
            stats.setYearValue(year);
            stats.setMonthValue(month);
        }

        stats.setProgressRecorded(stats.getProgressRecorded() + 1);
        userStatisticsRepository.save(stats);
    }
}
