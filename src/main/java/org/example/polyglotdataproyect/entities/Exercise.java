package org.example.polyglotdataproyect.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("type")
    private String type; // cardio, fuerza, movilidad

    @Field("description")
    private String description;

    @Field("duration_min")
    private Integer durationMin;

    @Field("difficulty")
    private String difficulty; // baja, media, alta

    @Field("video_url")
    private String videoUrl;

    @Field("created_by")
    private String createdBy; // ObjectId del usuario (entrenador) que lo creó
}
