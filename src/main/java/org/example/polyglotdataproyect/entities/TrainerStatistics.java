package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TRAINER_STATISTICS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"trainer_id", "stat_month", "stat_year"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trainer_id", nullable = false, length = 30)
    private String trainerId; // username from USERS table

    @Column(name = "stat_month", nullable = false)
    private Integer month; // 1-12

    @Column(name = "stat_year", nullable = false)
    private Integer year;

    @Column(name = "new_assignments_count", nullable = false)
    private Integer newAssignmentsCount = 0;

    @Column(name = "feedbacks_given_count", nullable = false)
    private Integer feedbacksGivenCount = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
