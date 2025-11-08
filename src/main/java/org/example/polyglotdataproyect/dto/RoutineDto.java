package org.example.polyglotdataproyect.dto;

import java.util.List;

public class RoutineDto {
    private String id;
    private String name;
    private String description;
    private List<String> exerciseIds;
    private boolean isPredefined;
    private String createdByTrainer;
    private String originalRoutineId;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getExerciseIds() {
        return exerciseIds;
    }

    public void setExerciseIds(List<String> exerciseIds) {
        this.exerciseIds = exerciseIds;
    }

    public boolean isPredefined() {
        return isPredefined;
    }

    public void setPredefined(boolean predefined) {
        isPredefined = predefined;
    }

    public String getCreatedByTrainer() {
        return createdByTrainer;
    }

    public void setCreatedByTrainer(String createdByTrainer) {
        this.createdByTrainer = createdByTrainer;
    }

    public String getOriginalRoutineId() {
        return originalRoutineId;
    }

    public void setOriginalRoutineId(String originalRoutineId) {
        this.originalRoutineId = originalRoutineId;
    }
}
