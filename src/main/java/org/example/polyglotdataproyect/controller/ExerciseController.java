package org.example.polyglotdataproyect.controller;

import org.example.polyglotdataproyect.dto.ExerciseDto;
import org.example.polyglotdataproyect.entities.Exercise;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.ExerciseRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public ExerciseController(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    // Crear ejercicio (puede ser predefinido si el usuario es entrenador, o personalizado)
    @PostMapping("/{username}")
    public ResponseEntity<Exercise> createExercise(@PathVariable String username, @RequestBody ExerciseDto exerciseDto) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.getName());
        exercise.setType(exerciseDto.getType());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setDuration(exerciseDto.getDuration());
        exercise.setDifficulty(exerciseDto.getDifficulty());
        exercise.setVideos(exerciseDto.getVideos());
        exercise.setCreatedByUsername(username);

        // Si el usuario es entrenador (role = "TRAINER"), el ejercicio es predefinido
        exercise.setPredefined(user.getRole().equals("TRAINER"));

        Exercise savedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExercise);
    }

    // Obtener todos los ejercicios predefinidos
    @GetMapping("/predefined")
    public ResponseEntity<List<Exercise>> getPredefinedExercises() {
        List<Exercise> exercises = exerciseRepository.findByIsPredefined(true);
        return ResponseEntity.ok(exercises);
    }

    // Obtener ejercicios por tipo
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Exercise>> getExercisesByType(@PathVariable String type) {
        List<Exercise> exercises = exerciseRepository.findByType(type);
        return ResponseEntity.ok(exercises);
    }

    // Obtener ejercicios creados por un usuario
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Exercise>> getExercisesByUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Exercise> exercises = exerciseRepository.findByCreatedByUsername(username);
        return ResponseEntity.ok(exercises);
    }

    // Obtener todos los ejercicios
    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return ResponseEntity.ok(exercises);
    }

    // Obtener ejercicio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable String id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        return exercise.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar ejercicio
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable String id, @RequestBody ExerciseDto exerciseDto) {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if (optionalExercise.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Exercise exercise = optionalExercise.get();
        exercise.setName(exerciseDto.getName());
        exercise.setType(exerciseDto.getType());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setDuration(exerciseDto.getDuration());
        exercise.setDifficulty(exerciseDto.getDifficulty());
        exercise.setVideos(exerciseDto.getVideos());

        Exercise updatedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.ok(updatedExercise);
    }

    // Eliminar ejercicio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable String id) {
        if (!exerciseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        exerciseRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
