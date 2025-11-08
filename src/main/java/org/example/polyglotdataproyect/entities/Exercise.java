package org.example.polyglotdataproyect.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "exercises")
public class Exercise {

    @Id
    private String id;

    private String name;
    private String type; // cardio, fuerza, movilidad
    private String description;
    private int duration; // en minutos
    private String difficulty; // facil, intermedio, dificil
    private List<String> videos; // URLs de videos demostrativos

    // Indica si es un ejercicio predefinido (creado por entrenador) o personalizado (creado por usuario)
    private boolean isPredefined;
    private String createdByUsername; // username del entrenador o usuario que lo creo

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public boolean isPredefined() {
        return isPredefined;
    }

    public void setPredefined(boolean predefined) {
        isPredefined = predefined;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public void setCreatedByUsername(String createdByUsername) {
        this.createdByUsername = createdByUsername;
    }
}
