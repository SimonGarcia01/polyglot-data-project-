package org.example.polyglotdataproyect.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trainer_statistics")
public class TrainerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String trainerUsername;

    @Column(nullable = false, name = "year_value")
    private int yearValue;

    @Column(nullable = false, name = "month_value") 
    private int monthValue; // 1-12  

    // Cantidad de asignaciones nuevas en el mes
    @Column(nullable = false)
    private int newAssignments = 0;

    // Cantidad de seguimientos/recomendaciones realizadas en el mes
    @Column(nullable = false)
    private int recommendationsMade = 0;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public int getYearValue() {
        return yearValue;
    }

    public void setYearValue(int yearValue) {
        this.yearValue = yearValue;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }

    public int getNewAssignments() {
        return newAssignments;
    }

    public void setNewAssignments(int newAssignments) {
        this.newAssignments = newAssignments;
    }

    public int getRecommendationsMade() {
        return recommendationsMade;
    }

    public void setRecommendationsMade(int recommendationsMade) {
        this.recommendationsMade = recommendationsMade;
    }
}
