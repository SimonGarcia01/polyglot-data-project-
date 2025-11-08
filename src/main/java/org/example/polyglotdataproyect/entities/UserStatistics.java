package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_statistics")
public class UserStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int month; // 1-12

    // Cantidad de rutinas que ha iniciado en el mes
    @Column(nullable = false)
    private int routinesStarted = 0;

    // Cantidad de veces que ha realizado seguimiento en el mes
    @Column(nullable = false)
    private int progressRecorded = 0;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getRoutinesStarted() {
        return routinesStarted;
    }

    public void setRoutinesStarted(int routinesStarted) {
        this.routinesStarted = routinesStarted;
    }

    public int getProgressRecorded() {
        return progressRecorded;
    }

    public void setProgressRecorded(int progressRecorded) {
        this.progressRecorded = progressRecorded;
    }
}
