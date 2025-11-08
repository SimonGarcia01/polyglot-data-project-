package org.example.polyglotdataproyect.dto;

import java.time.LocalDate;

public class ProgressDto {
    private String routineId;
    private LocalDate progressDate;
    private String metrics;
    private String notes;
    private String trainerRecommendation;

    // Getters and setters
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
}
