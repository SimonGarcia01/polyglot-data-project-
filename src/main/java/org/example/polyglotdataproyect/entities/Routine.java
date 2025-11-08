package org.example.polyglotdataproyect.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "routines")
public class Routine {

    @Id
    private String id;

    private String name;
    private String description;

    // Username del usuario propietario de la rutina
    private String username;

    // Lista de IDs de ejercicios en lugar de objetos completos para mejor performance
    private List<String> exerciseIds;

    // Indica si es una rutina prediseñada por un entrenador
    private boolean isPredefined;

    // Si es prediseñada, username del entrenador que la creó
    private String createdByTrainer;

    // Si es una copia de una rutina prediseñada, guarda el ID de la rutina original
    private String originalRoutineId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
