package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.TrainerTrainee;
import org.example.polyglotdataproyect.repositories.TrainerTraineeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AssignmentService {

    @Autowired
    private TrainerTraineeRepository trainerTraineeRepository;

    public List<TrainerTrainee> getAllAssignments() {
        return trainerTraineeRepository.findAll();
    }

    public List<TrainerTrainee> getAssignmentsByTrainer(String trainerId) {
        return trainerTraineeRepository.findByTrainerId(trainerId);
    }

    public Optional<TrainerTrainee> getAssignmentByTrainee(String traineeId) {
        return trainerTraineeRepository.findByTraineeId(traineeId);
    }

    public TrainerTrainee assignTrainerToTrainee(String trainerId, String traineeId) {
        // Primero verificamos si ya existe una asignación para este trainee
        Optional<TrainerTrainee> existingAssignment = trainerTraineeRepository.findByTraineeId(traineeId);

        if (existingAssignment.isPresent()) {
            // Si ya existe, actualizamos el trainer
            TrainerTrainee assignment = existingAssignment.get();
            assignment.setTrainerId(trainerId);
            assignment.setAssignedDate(new Date());
            return trainerTraineeRepository.save(assignment);
        } else {
            // Si no existe, creamos una nueva asignación
            TrainerTrainee assignment = new TrainerTrainee();
            assignment.setTrainerId(trainerId);
            assignment.setTraineeId(traineeId);
            assignment.setAssignedDate(new Date());
            return trainerTraineeRepository.save(assignment);
        }
    }

    @Transactional
    public void removeAssignment(String trainerId, String traineeId) {
        trainerTraineeRepository.deleteByTrainerIdAndTraineeId(trainerId, traineeId);
    }

    public void deleteAssignment(String assignmentId) {
        trainerTraineeRepository.deleteById(assignmentId);
    }
}
