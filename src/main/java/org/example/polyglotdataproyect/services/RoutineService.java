package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.Routine;
import org.example.polyglotdataproyect.repositories.RoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    public List<Routine> getAllRoutines() {
        return routineRepository.findAll();
    }

    public Routine saveRoutine(Routine routine) {
        return routineRepository.save(routine);
    }

    public void deleteRoutine(String id) {
        routineRepository.deleteById(id);
    }
}
