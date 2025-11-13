package org.example.polyglotdataproyect.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongoUser {

    @Id
    private String id;

    @Field("sql_user_id")
    private String sqlUserId;

    @Field("name")
    private String name;

    @Field("role")
    private String role; // student, trainer, collaborator

    @Field("email")
    private String email;

    @Field("created_at")
    private Date createdAt;
}
