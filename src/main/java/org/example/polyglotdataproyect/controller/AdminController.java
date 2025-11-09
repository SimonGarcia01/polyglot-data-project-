package org.example.polyglotdataproyect.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.example.polyglotdataproyect.entities.TrainerAssignment;
import org.example.polyglotdataproyect.entities.TrainerStatistics;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.TrainerAssignmentRepository;
import org.example.polyglotdataproyect.repositories.TrainerStatisticsRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TrainerAssignmentRepository trainerAssignmentRepository;
    private final UserRepository userRepository;
    private final TrainerStatisticsRepository trainerStatisticsRepository;

    public AdminController(TrainerAssignmentRepository trainerAssignmentRepository,
                          UserRepository userRepository,
                          TrainerStatisticsRepository trainerStatisticsRepository) {
        this.trainerAssignmentRepository = trainerAssignmentRepository;
        this.userRepository = userRepository;
        this.trainerStatisticsRepository = trainerStatisticsRepository;
    }

    // Asignar un entrenador a un estudiante
    @PostMapping("/assign")
    public ResponseEntity<TrainerAssignment> assignTrainer(@RequestBody Map<String, String> request) {
        String trainerUsername = request.get("trainerUsername");
        String studentUsername = request.get("studentUsername");

        // Verificar que el entrenador existe y tiene el rol correcto
        User trainer = userRepository.findByUsername(trainerUsername);
        if (trainer == null || !trainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Verificar que el estudiante existe
        User student = userRepository.findByUsername(studentUsername);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Verificar si el estudiante ya tiene un entrenador asignado
        Optional<TrainerAssignment> existingAssignment = trainerAssignmentRepository.findByStudentUsernameAndIsActive(studentUsername, true);
        if (existingAssignment.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(null); // Ya tiene un entrenador activo
        }

        // Crear nueva asignación
        TrainerAssignment assignment = new TrainerAssignment();
        assignment.setTrainerUsername(trainerUsername);
        assignment.setStudentUsername(studentUsername);
        assignment.setAssignedAt(LocalDateTime.now());
        assignment.setActive(true);

        TrainerAssignment savedAssignment = trainerAssignmentRepository.save(assignment);

        // Actualizar estadísticas del entrenador
        updateTrainerStatistics(trainerUsername);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAssignment);
    }

    // Modificar asignación (cambiar de entrenador)
    @PutMapping("/assign/{studentUsername}")
    public ResponseEntity<TrainerAssignment> reassignTrainer(@PathVariable String studentUsername, @RequestBody Map<String, String> request) {
        String newTrainerUsername = request.get("trainerUsername");

        // Verificar que el nuevo entrenador existe y tiene el rol correcto
        User newTrainer = userRepository.findByUsername(newTrainerUsername);
        if (newTrainer == null || !newTrainer.getRole().equals("TRAINER")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Verificar que el estudiante existe
        User student = userRepository.findByUsername(studentUsername);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Desactivar asignación anterior
        Optional<TrainerAssignment> existingAssignment = trainerAssignmentRepository.findByStudentUsernameAndIsActive(studentUsername, true);
        if (existingAssignment.isPresent()) {
            TrainerAssignment oldAssignment = existingAssignment.get();
            oldAssignment.setActive(false);
            oldAssignment.setUnassignedAt(LocalDateTime.now());
            trainerAssignmentRepository.save(oldAssignment);
        }

        // Crear nueva asignación
        TrainerAssignment newAssignment = new TrainerAssignment();
        newAssignment.setTrainerUsername(newTrainerUsername);
        newAssignment.setStudentUsername(studentUsername);
        newAssignment.setAssignedAt(LocalDateTime.now());
        newAssignment.setActive(true);

        TrainerAssignment savedAssignment = trainerAssignmentRepository.save(newAssignment);

        // Actualizar estadísticas del nuevo entrenador
        updateTrainerStatistics(newTrainerUsername);

        return ResponseEntity.ok(savedAssignment);
    }

    // Desasignar un entrenador de un estudiante
    @DeleteMapping("/assign/{studentUsername}")
    public ResponseEntity<Void> unassignTrainer(@PathVariable String studentUsername) {
        Optional<TrainerAssignment> assignment = trainerAssignmentRepository.findByStudentUsernameAndIsActive(studentUsername, true);

        if (assignment.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        TrainerAssignment activeAssignment = assignment.get();
        activeAssignment.setActive(false);
        activeAssignment.setUnassignedAt(LocalDateTime.now());
        trainerAssignmentRepository.save(activeAssignment);

        return ResponseEntity.noContent().build();
    }

    // Ver todas las asignaciones activas
    @GetMapping("/assignments/active")
    public ResponseEntity<List<TrainerAssignment>> getActiveAssignments() {
        List<TrainerAssignment> assignments = trainerAssignmentRepository.findAll()
                .stream()
                .filter(TrainerAssignment::isActive)
                .toList();
        return ResponseEntity.ok(assignments);
    }

    // Ver todas las asignaciones (activas e inactivas)
    @GetMapping("/assignments")
    public ResponseEntity<List<TrainerAssignment>> getAllAssignments() {
        List<TrainerAssignment> assignments = trainerAssignmentRepository.findAll();
        return ResponseEntity.ok(assignments);
    }

    // Ver asignaciones de un estudiante específico
    @GetMapping("/assignments/student/{studentUsername}")
    public ResponseEntity<List<TrainerAssignment>> getStudentAssignments(@PathVariable String studentUsername) {
        List<TrainerAssignment> assignments = trainerAssignmentRepository.findByStudentUsername(studentUsername);
        return ResponseEntity.ok(assignments);
    }

    // Ver asignaciones de un entrenador específico
    @GetMapping("/assignments/trainer/{trainerUsername}")
    public ResponseEntity<List<TrainerAssignment>> getTrainerAssignments(@PathVariable String trainerUsername) {
        List<TrainerAssignment> assignments = trainerAssignmentRepository.findByTrainerUsername(trainerUsername);
        return ResponseEntity.ok(assignments);
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

        stats.setNewAssignments(stats.getNewAssignments() + 1);
        trainerStatisticsRepository.save(stats);
    }
}
