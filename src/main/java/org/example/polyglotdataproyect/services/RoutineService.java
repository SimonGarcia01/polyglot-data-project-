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
        return routineRepository.save(routine);
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
            copiedRoutine.setExercises(originalRoutine.getExercises());
            copiedRoutine.setCreatedAt(new Date());
            return routineRepository.save(copiedRoutine);
        }
        return null;
    }

    public Routine updateRoutine(String id, Routine routine) {
        if (routineRepository.existsById(id)) {
            routine.setId(id);
            return routineRepository.save(routine);
        }
        return null;
    }

    public void deleteRoutine(String id) {
        routineRepository.deleteById(id);
    }
}
