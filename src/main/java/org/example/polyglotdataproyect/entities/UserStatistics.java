package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USER_STATISTICS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "stat_month", "stat_year"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, length = 30)
    private String userId; // username from USERS table

    @Column(name = "stat_month", nullable = false)
    private Integer month; // 1-12

    @Column(name = "stat_year", nullable = false)
    private Integer year;

    @Column(name = "routines_started", nullable = false)
    private Integer routinesStarted = 0;

    @Column(name = "progress_logs_count", nullable = false)
    private Integer progressLogsCount = 0;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
