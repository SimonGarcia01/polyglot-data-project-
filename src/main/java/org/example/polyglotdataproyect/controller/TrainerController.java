package org.example.polyglotdataproyect.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.polyglotdataproyect.entities.Progress;
import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.entities.TrainerAssignment;
import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.ProgressRepository;
import org.example.polyglotdataproyect.repositories.RoutineRepository;
import org.example.polyglotdataproyect.repositories.TrainerAssignmentRepository;
import org.example.polyglotdataproyect.repositories.TrainerStatisticsRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    private final TrainerAssignmentRepository trainerAssignmentRepository;
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    private final ProgressRepository progressRepository;
    private final TrainerStatisticsRepository trainerStatisticsRepository;

    public TrainerController(TrainerAssignmentRepository trainerAssignmentRepository,
                           UserRepository userRepository,
                           RoutineRepository routineRepository,
                           ProgressRepository progressRepository,
                           TrainerStatisticsRepository trainerStatisticsRepository) {
        this.trainerAssignmentRepository = trainerAssignmentRepository;
        this.userRepository = userRepository;
        this.routineRepository = routineRepository;
        this.progressRepository = progressRepository;
        this.trainerStatisticsRepository = trainerStatisticsRepository;
    }

    // Obtener estudiantes asignados a un entrenador
    @GetMapping("/{trainerUsername}/students")
    public ResponseEntity<List<Map<String, String>>> getAssignedStudents(@PathVariable String trainerUsername) {
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<TrainerAssignment> assignments = trainerAssignmentRepository.findByTrainerUsernameAndIsActive(trainerUsername, true);
        List<Map<String, String>> students = assignments.stream()
                .map(assignment -> {
                    Map<String, String> student = new HashMap<>();
                    student.put("username", assignment.getStudentUsername());
                    student.put("assignedAt", assignment.getAssignedAt().toString());
                    return student;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(students);
    }

    // Ver rutinas de un estudiante asignado
    @GetMapping("/{trainerUsername}/student/{studentUsername}/routines")
    public ResponseEntity<List<Routine>> getStudentRoutines(@PathVariable String trainerUsername, @PathVariable String studentUsername) {
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Verificar que el estudiante está asignado al entrenador
        Optional<TrainerAssignment> assignment = trainerAssignmentRepository.findByStudentUsernameAndIsActive(studentUsername, true);
        if (assignment.isEmpty() || !assignment.get().getTrainerUsername().equals(trainerUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Routine> routines = routineRepository.findByUsername(studentUsername);
        return ResponseEntity.ok(routines);
    }

    // Ver progreso de un estudiante asignado
    @GetMapping("/{trainerUsername}/student/{studentUsername}/progress")
    public ResponseEntity<List<Progress>> getStudentProgress(@PathVariable String trainerUsername, @PathVariable String studentUsername) {
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Verificar que el estudiante está asignado al entrenador
        Optional<TrainerAssignment> assignment = trainerAssignmentRepository.findByStudentUsernameAndIsActive(studentUsername, true);
        if (assignment.isEmpty() || !assignment.get().getTrainerUsername().equals(trainerUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Progress> progressList = progressRepository.findByUsername(studentUsername);
        return ResponseEntity.ok(progressList);
    }

    // Crear recomendación para un progreso
    @PostMapping("/{trainerUsername}/progress/{progressId}/recommend")
    public ResponseEntity<Progress> addRecommendation(@PathVariable String trainerUsername,
                                                      @PathVariable String progressId,
                                                      @RequestBody Map<String, String> request) {
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Progress> progressOpt = progressRepository.findById(progressId);
        if (progressOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Progress progress = progressOpt.get();

        // Verificar que el estudiante está asignado al entrenador
        Optional<TrainerAssignment> assignment = trainerAssignmentRepository.findByStudentUsernameAndIsActive(progress.getUsername(), true);
        if (assignment.isEmpty() || !assignment.get().getTrainerUsername().equals(trainerUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        String recommendation = request.get("recommendation");
        progress.setTrainerRecommendation(recommendation);
        progress.setTrainerUsername(trainerUsername);

        Progress updatedProgress = progressRepository.save(progress);

        // Actualizar estadísticas del entrenador
        updateTrainerStatistics(trainerUsername);

        return ResponseEntity.ok(updatedProgress);
    }

    // Crear rutina prediseñada
    @PostMapping("/{trainerUsername}/routine/predefined")
    public ResponseEntity<Routine> createPredefinedRoutine(@PathVariable String trainerUsername, @RequestBody Routine routine) {
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        routine.setPredefined(true);
        routine.setCreatedByTrainer(trainerUsername);
        routine.setUsername(trainerUsername); // El entrenador es el propietario
        routine.setCreatedAt(LocalDateTime.now());
        routine.setUpdatedAt(LocalDateTime.now());

        Routine savedRoutine = routineRepository.save(routine);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoutine);
    }

    // Ver rutinas prediseñadas creadas por el entrenador
    @GetMapping("/{trainerUsername}/routines/predefined")
    public ResponseEntity<List<Routine>> getTrainerPredefinedRoutines(@PathVariable String trainerUsername) {
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Routine> routines = routineRepository.findByCreatedByTrainer(trainerUsername);
        return ResponseEntity.ok(routines);
    }

    // Método auxiliar para actualizar estadísticas del entrenador
    private void updateTrainerStatistics(String trainerUsername) {
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        int month = now.getMonthValue();

        Optional<TrainerStatistics> statsOpt = trainerStatisticsRepository.findByTrainerUsernameAndYearValueAndMonthValue(trainerUsername, year, month);
        TrainerStatistics stats;

        if (statsOpt.isPresent()) {
            stats = statsOpt.get();
        } else {
            stats = new TrainerStatistics();
            stats.setTrainerUsername(trainerUsername);
            stats.setYearValue(year);
            stats.setMonthValue(month);
        }

        stats.setRecommendationsMade(stats.getRecommendationsMade() + 1);
        trainerStatisticsRepository.save(stats);
    }
}
