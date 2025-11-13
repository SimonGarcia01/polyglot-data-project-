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

    public Exercise updateExercise(String id, Exercise exercise, String currentUserId, String currentUserRole) {
        Optional<Exercise> existingOpt = exerciseRepository.findById(id);

        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Exercise not found");
        }

        Exercise existing = existingOpt.get();

        // Validar ownership: el creador puede editar, o TRAINER/ADMIN pueden editar cualquiera
        boolean isCreator = existing.getCreatedBy() != null && existing.getCreatedBy().equals(currentUserId);
        boolean isTrainerOrAdmin = "TRAINER".equals(currentUserRole) || "ADMIN".equals(currentUserRole);

        if (!isCreator && !isTrainerOrAdmin) {
            throw new RuntimeException("You can only edit your own exercises or you need to be a trainer/admin");
        }

        exercise.setId(id);
        // Preservar createdBy original si no se proporciona
        if (exercise.getCreatedBy() == null) {
            exercise.setCreatedBy(existing.getCreatedBy());
        }
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(String id, String currentUserId, String currentUserRole) {
        Optional<Exercise> existingOpt = exerciseRepository.findById(id);

        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Exercise not found");
        }

        Exercise existing = existingOpt.get();

        // Validar ownership: el creador puede eliminar, o ADMIN puede eliminar cualquiera
        boolean isCreator = existing.getCreatedBy() != null && existing.getCreatedBy().equals(currentUserId);
        boolean isAdmin = "ADMIN".equals(currentUserRole);

        if (!isCreator && !isAdmin) {
            throw new RuntimeException("You can only delete your own exercises or you need to be an admin");
        }

        exerciseRepository.deleteById(id);
    }

    /**
     * Versión sin validación para uso interno/admin
     */
    public void deleteExerciseById(String id) {
        exerciseRepository.deleteById(id);
    }
}
