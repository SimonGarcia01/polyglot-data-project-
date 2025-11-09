package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.MongoUser;
import org.example.polyglotdataproyect.entities.TrainerTrainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private MongoUserService mongoUserService;

    public TrainerTrainee assignTrainer(String trainerId, String traineeId) {
        return assignmentService.assignTrainerToTrainee(trainerId, traineeId);
    }

    public List<TrainerTrainee> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    public List<TrainerTrainee> getAssignmentsByTrainer(String trainerId) {
        return assignmentService.getAssignmentsByTrainer(trainerId);
    }

    public void removeAssignment(String trainerId, String traineeId) {
        assignmentService.removeAssignment(trainerId, traineeId);
    }

    public void deleteAssignment(String assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
    }

    public List<MongoUser> getAllTrainers() {
        return mongoUserService.getAllTrainers();
    }

    public List<MongoUser> getAllStudents() {
        return mongoUserService.getAllStudents();
    }

    public List<MongoUser> getAllCollaborators() {
        return mongoUserService.getAllCollaborators();
    }

    public List<MongoUser> getAllUsers() {
        return mongoUserService.getAllUsers();
    }
}
