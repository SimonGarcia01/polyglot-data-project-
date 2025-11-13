package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.repositories.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private StatisticsService statisticsService;

    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    public Optional<Routine> getRoutineById(String id) {
        return routineRepository.findById(id);
    }

    public List<Routine> getRoutinesByOwner(String ownerId) {
        return routineRepository.findByOwnerId(ownerId);
    }

    public List<Routine> getTemplateRoutines() {
        return routineRepository.findByIsTemplate(true);
    }

    public List<Routine> getUserPersonalRoutines(String ownerId) {
        return routineRepository.findByOwnerId(ownerId);
    }

    public List<Routine> searchRoutinesByName(String name) {
        return routineRepository.findByNameContainingIgnoreCase(name);
    }

    public Routine createRoutine(Routine routine) {
        if (routine.getCreatedAt() == null) {
            routine.setCreatedAt(new Date());
        }
        if (routine.getIsTemplate() == null) {
            routine.setIsTemplate(false);
        }
        if (routine.getExercises() == null) {
            routine.setExercises(new java.util.ArrayList<>());
        }
        Routine saved = routineRepository.save(routine);

        // Actualizar estadísticas: incrementar rutinas iniciadas
        if (routine.getOwnerId() != null && !Boolean.TRUE.equals(routine.getIsTemplate())) {
            statisticsService.incrementRoutineStarted(routine.getOwnerId());
        }

        return saved;
    }

    public Routine copyRoutineForUser(String routineId, String newOwnerId) {
        Optional<Routine> originalRoutineOpt = routineRepository.findById(routineId);
        if (originalRoutineOpt.isPresent()) {
            Routine originalRoutine = originalRoutineOpt.get();
            Routine copiedRoutine = new Routine();
            copiedRoutine.setName(originalRoutine.getName() + " (Copia)");
            copiedRoutine.setOwnerId(newOwnerId);
            copiedRoutine.setIsTemplate(false);
            copiedRoutine.setCopiedFrom(routineId);
            copiedRoutine.setExercises(originalRoutine.getExercises() != null ?
                originalRoutine.getExercises() : new java.util.ArrayList<>());
            copiedRoutine.setCreatedAt(new Date());
            Routine saved = routineRepository.save(copiedRoutine);

            // Actualizar estadísticas: incrementar rutinas iniciadas
            statisticsService.incrementRoutineStarted(newOwnerId);

            return saved;
        }
        return null;
    }

    public Routine updateRoutine(String id, Routine routine, String currentUserId) {
        Optional<Routine> existingOpt = routineRepository.findById(id);

        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Routine not found");
        }

        Routine existing = existingOpt.get();

        // Validar ownership: solo el dueño puede editar (templates pueden ser editadas por trainers/admin)
        if (!existing.getOwnerId().equals(currentUserId) && !Boolean.TRUE.equals(existing.getIsTemplate())) {
            throw new RuntimeException("You can only edit your own routines");
        }

        routine.setId(id);
        // Preservar ownerId original
        routine.setOwnerId(existing.getOwnerId());
        // Asegurar que exercises nunca sea null
        if (routine.getExercises() == null) {
            routine.setExercises(new java.util.ArrayList<>());
        }
        return routineRepository.save(routine);
    }

    public void deleteRoutine(String id, String currentUserId) {
        Optional<Routine> existingOpt = routineRepository.findById(id);

        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Routine not found");
        }

        Routine existing = existingOpt.get();

        // Validar ownership: solo el dueño puede eliminar
        if (!existing.getOwnerId().equals(currentUserId)) {
            throw new RuntimeException("You can only delete your own routines");
        }

        routineRepository.deleteById(id);
    }

    /**
     * Versión sin validación para uso interno/admin
     */
    public void deleteRoutineById(String id) {
        routineRepository.deleteById(id);
    }
}
