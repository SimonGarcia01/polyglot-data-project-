package org.example.polyglotdataproyect.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "trainer_trainee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerTrainee {

    @Id
    private String id;

    @Field("trainer_id")
    private String trainerId; // ObjectId del entrenador

    @Field("trainee_id")
    private String traineeId; // ObjectId del aprendiz (usuario)

    @Field("assigned_date")
    private Date assignedDate;
}
