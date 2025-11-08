package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "trainer_statistics")
public class TrainerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String trainerUsername;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month; // 1-12

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
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
