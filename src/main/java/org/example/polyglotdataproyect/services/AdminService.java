package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.TrainerAssignment;
import org.example.polyglotdataproyect.repositories.TrainerAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private TrainerAssignmentRepository trainerAssignmentRepository;

    public TrainerAssignment assignTrainer(TrainerAssignment assignment) {
        return trainerAssignmentRepository.save(assignment);
    }

    public java.util.List<TrainerAssignment> getAllAssignments() {
        return trainerAssignmentRepository.findAll();
    }
}
