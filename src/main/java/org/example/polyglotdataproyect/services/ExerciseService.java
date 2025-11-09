package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.Exercise;
import org.example.polyglotdataproyect.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(String id) {
        return exerciseRepository.findById(id);
    }

    public List<Exercise> getExercisesByType(String type) {
        return exerciseRepository.findByType(type);
    }

    public List<Exercise> getExercisesByDifficulty(String difficulty) {
        return exerciseRepository.findByDifficulty(difficulty);
    }

    public List<Exercise> getExercisesByCreator(String creatorId) {
        return exerciseRepository.findByCreatedBy(creatorId);
    }

    public List<Exercise> searchExercisesByName(String name) {
        return exerciseRepository.findByNameContainingIgnoreCase(name);
    }

    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise updateExercise(String id, Exercise exercise) {
        if (exerciseRepository.existsById(id)) {
            exercise.setId(id);
            return exerciseRepository.save(exercise);
        }
        return null;
    }

    public void deleteExercise(String id) {
        exerciseRepository.deleteById(id);
    }
}
