package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.ProgressEntry;
import org.example.polyglotdataproyect.repositories.ProgressEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProgressService {

    @Autowired
    private ProgressEntryRepository progressEntryRepository;

    public List<ProgressEntry> getAllProgressEntries() {
        return progressEntryRepository.findAll();
    }

    public Optional<ProgressEntry> getProgressEntryById(String id) {
        return progressEntryRepository.findById(id);
    }

    public List<ProgressEntry> getProgressEntriesByUser(String userId) {
        return progressEntryRepository.findByUserId(userId);
    }

    public List<ProgressEntry> getProgressEntriesByRoutine(String routineId) {
        return progressEntryRepository.findByRoutineId(routineId);
    }

    public Optional<ProgressEntry> getProgressEntryByUserAndRoutine(String userId, String routineId) {
        return progressEntryRepository.findByUserIdAndRoutineId(userId, routineId);
    }

    public ProgressEntry addProgressEntry(String userId, String routineId, Integer completedExercises, Integer effortLevel) {
        Optional<ProgressEntry> existingProgressOpt = progressEntryRepository.findByUserIdAndRoutineId(userId, routineId);

        ProgressEntry.Entry newEntry = new ProgressEntry.Entry();
        newEntry.setDate(new Date());
        newEntry.setCompletedExercises(completedExercises);
        newEntry.setEffortLevel(effortLevel);

        if (existingProgressOpt.isPresent()) {
            // Si ya existe un documento de progreso, agregamos la nueva entrada
            ProgressEntry progressEntry = existingProgressOpt.get();
            progressEntry.getEntries().add(newEntry);
            return progressEntryRepository.save(progressEntry);
        } else {
            // Si no existe, creamos un nuevo documento
            ProgressEntry progressEntry = new ProgressEntry();
            progressEntry.setUserId(userId);
            progressEntry.setRoutineId(routineId);
            List<ProgressEntry.Entry> entries = new ArrayList<>();
            entries.add(newEntry);
            progressEntry.setEntries(entries);
            return progressEntryRepository.save(progressEntry);
        }
    }

    public ProgressEntry addTrainerFeedback(String userId, String routineId, int entryIndex, String feedback) {
        Optional<ProgressEntry> progressOpt = progressEntryRepository.findByUserIdAndRoutineId(userId, routineId);

        if (progressOpt.isPresent()) {
            ProgressEntry progressEntry = progressOpt.get();
            if (entryIndex >= 0 && entryIndex < progressEntry.getEntries().size()) {
                progressEntry.getEntries().get(entryIndex).setTrainerFeedback(feedback);
                return progressEntryRepository.save(progressEntry);
            }
        }
        return null;
    }

    public void deleteProgressEntry(String id) {
        progressEntryRepository.deleteById(id);
    }
}
