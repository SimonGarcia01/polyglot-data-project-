package org.example.polyglotdataproyect.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "progress")
public class Progress {

    @Id
    private String id;

    // Username del usuario
    private String username;

    // ID de la rutina
    private String routineId;

    private LocalDate progressDate;
    private String metrics; // e.g., "repetitions:10, time:30min, effort:high"

    // Comentario o nota del usuario sobre su progreso
    private String notes;

    // Recomendación del entrenador (si la hay)
    private String trainerRecommendation;
    private String trainerUsername; // quien hizo la recomendación

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoutineId() {
        return routineId;
    }

    public void setRoutineId(String routineId) {
        this.routineId = routineId;
    }

    public LocalDate getProgressDate() {
        return progressDate;
    }

    public void setProgressDate(LocalDate progressDate) {
        this.progressDate = progressDate;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTrainerRecommendation() {
        return trainerRecommendation;
    }

    public void setTrainerRecommendation(String trainerRecommendation) {
        this.trainerRecommendation = trainerRecommendation;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }
}
