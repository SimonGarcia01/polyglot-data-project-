package org.example.polyglotdataproyect.services;

import org.example.polyglotdataproyect.entities.Progress;
import org.example.polyglotdataproyect.repositories.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    public List<Progress> getProgressByUsername(String username) {
        return progressRepository.findByUsername(username);
    }

    public Progress logProgress(Progress progress) {
        return progressRepository.save(progress);
    }
}
