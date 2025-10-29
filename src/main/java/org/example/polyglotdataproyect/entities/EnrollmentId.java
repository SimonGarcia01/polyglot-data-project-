package org.example.polyglotdataproyect.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EnrollmentId implements Serializable {

    @Column(nullable = false, length = 15)
    private String student;

    @Column(nullable = false, length = 10)
    private String group;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o instanceof EnrollmentId that){
            return student.equals(that.student) && group.equals(that.group);
        } else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, group);
    }
}