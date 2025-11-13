package org.example.polyglotdataproyect.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Document(collection = "progress_entry")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgressEntry {

    @Id
    private String id;

    @Field("user_id")
    private String userId; // ObjectId del usuario

    @Field("routine_id")
    private String routineId; // ObjectId de la rutina

    @Field("entries")
    private List<Entry> entries;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Entry {
        @Field("date")
        private Date date;

        @Field("completed_exercises")
        private Integer completedExercises;

        @Field("effort_level")
        private Integer effortLevel; // Nivel de esfuerzo percibido (1-10)

        @Field("trainer_feedback")
        private String trainerFeedback;
    }
}
