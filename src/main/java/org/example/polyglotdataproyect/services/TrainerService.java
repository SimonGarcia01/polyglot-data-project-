package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.TrainerAssignment;
import org.example.polyglotdataproyect.entities.User;
import org.example.polyglotdataproyect.repositories.TrainerAssignmentRepository;
import org.example.polyglotdataproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerService {

    @Autowired
    private TrainerAssignmentRepository trainerAssignmentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAssignedUsers(String trainerUsername) {
        List<String> studentUsernames = trainerAssignmentRepository.findByTrainerUsername(trainerUsername)
                .stream()
                .map(TrainerAssignment::getStudentUsername)
                .collect(Collectors.toList());

        return userRepository.findAllById(studentUsernames);
    }
}
