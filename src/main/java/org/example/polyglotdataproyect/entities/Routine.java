package org.example.polyglotdataproyect.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "routines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Routine {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("owner_id")
    private String ownerId; // ObjectId del usuario dueño de la rutina

    @Field("is_template")
    private Boolean isTemplate;

    @Field("copied_from")
    private String copiedFrom; // ObjectId de la rutina original (si fue copiada)

    @Field("exercises")
    private List<ExerciseInRoutine> exercises;

    @Field("created_at")
    private Date createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExerciseInRoutine {
        @Field("exercise_id")
        private String exerciseId;

        @Field("sets")
        private Integer sets;

        @Field("reps")
        private Integer reps;

        @Field("duration_min")
        private Integer durationMin;
    }
}
