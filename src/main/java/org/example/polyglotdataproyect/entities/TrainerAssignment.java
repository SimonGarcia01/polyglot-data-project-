package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainer_assignments")
public class TrainerAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String trainerUsername;

    @Column(nullable = false, length = 30)
    private String studentUsername;

    @Column(nullable = false)
    private LocalDateTime assignedAt;

    private LocalDateTime unassignedAt;

    @Column(nullable = false)
    private boolean isActive = true;

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

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }

    public LocalDateTime getUnassignedAt() {
        return unassignedAt;
    }

    public void setUnassignedAt(LocalDateTime unassignedAt) {
        this.unassignedAt = unassignedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
