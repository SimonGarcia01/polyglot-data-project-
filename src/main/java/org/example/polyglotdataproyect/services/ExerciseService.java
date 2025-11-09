package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.Exercise;
import org.example.polyglotdataproyect.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(String id) {
        exerciseRepository.deleteById(id);
    }
}
