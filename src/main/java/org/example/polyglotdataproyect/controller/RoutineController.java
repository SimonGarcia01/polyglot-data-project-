package org.example.polyglotdataproyect.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.example.polyglotdataproyect.dto.RoutineDto;
import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.entities.UserStatistics;
import org.example.polyglotdataproyect.repositories.RoutineRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.example.polyglotdataproyect.repositories.UserStatisticsRepository;
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
@RequestMapping("/api/routines")
public class RoutineController {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    public RoutineController(RoutineRepository routineRepository, UserRepository userRepository, UserStatisticsRepository userStatisticsRepository) {
        this.routineRepository = routineRepository;
        this.userRepository = userRepository;
        this.userStatisticsRepository = userStatisticsRepository;
    }

    // Crear una rutina personal para un usuario
    @PostMapping("/{username}")
    public ResponseEntity<Routine> createRoutine(@PathVariable String username, @RequestBody RoutineDto routineDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Routine routine = new Routine();
        routine.setName(routineDto.getName());
        routine.setDescription(routineDto.getDescription());
        routine.setUsername(username);
        routine.setExerciseIds(routineDto.getExerciseIds());
        routine.setPredefined(false);
        routine.setCreatedAt(LocalDateTime.now());
        routine.setUpdatedAt(LocalDateTime.now());

        // Si es una copia de una rutina predefinida
        if (routineDto.getOriginalRoutineId() != null) {
            routine.setOriginalRoutineId(routineDto.getOriginalRoutineId());
        }

        Routine savedRoutine = routineRepository.save(routine);

        // Actualizar estadísticas
        updateUserStatistics(username, true, false);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoutine);
    }

    // Obtener rutinas de un usuario
    @GetMapping("/{username}")
    public ResponseEntity<List<Routine>> getRoutinesByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Routine> routines = routineRepository.findByUsername(username);
        return ResponseEntity.ok(routines);
    }

    // Obtener todas las rutinas predefinidas
    @GetMapping("/predefined")
    public ResponseEntity<List<Routine>> getPredefinedRoutines() {
        List<Routine> routines = routineRepository.findByIsPredefined(true);
        return ResponseEntity.ok(routines);
    }

    // Adoptar una rutina predefinida (hacer una copia)
    @PostMapping("/adopt/{routineId}/{username}")
    public ResponseEntity<Routine> adoptRoutine(@PathVariable String routineId, @PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Routine> originalRoutineOpt = routineRepository.findById(routineId);
        if (originalRoutineOpt.isEmpty() || !originalRoutineOpt.get().isPredefined()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Routine originalRoutine = originalRoutineOpt.get();

        // Crear una copia de la rutina
        Routine adoptedRoutine = new Routine();
        adoptedRoutine.setName(originalRoutine.getName() + " (Copia)");
        adoptedRoutine.setDescription(originalRoutine.getDescription());
        adoptedRoutine.setUsername(username);
        adoptedRoutine.setExerciseIds(originalRoutine.getExerciseIds());
        adoptedRoutine.setPredefined(false);
        adoptedRoutine.setOriginalRoutineId(routineId);
        adoptedRoutine.setCreatedAt(LocalDateTime.now());
        adoptedRoutine.setUpdatedAt(LocalDateTime.now());

        Routine savedRoutine = routineRepository.save(adoptedRoutine);

        // Actualizar estadísticas
        updateUserStatistics(username, true, false);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRoutine);
    }

    // Actualizar rutina
    @PutMapping("/{id}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable String id, @RequestBody RoutineDto routineDto) {
        Optional<Routine> optionalRoutine = routineRepository.findById(id);
        if (optionalRoutine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Routine routine = optionalRoutine.get();
        routine.setName(routineDto.getName());
        routine.setDescription(routineDto.getDescription());
        routine.setExerciseIds(routineDto.getExerciseIds());
        routine.setUpdatedAt(LocalDateTime.now());

        Routine updatedRoutine = routineRepository.save(routine);
        return ResponseEntity.ok(updatedRoutine);
    }

    // Eliminar rutina
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable String id) {
        if (!routineRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        routineRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para actualizar estadísticas de usuario
    private void updateUserStatistics(String username, boolean routineStarted, boolean progressRecorded) {
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

        if (routineStarted) {
            stats.setRoutinesStarted(stats.getRoutinesStarted() + 1);
        }
        if (progressRecorded) {
            stats.setProgressRecorded(stats.getProgressRecorded() + 1);
        }

        userStatisticsRepository.save(stats);
    }
}
