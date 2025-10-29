package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ENROLLMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @EmbeddedId
    private EnrollmentId enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("studentId")
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ncr")
    @JoinColumn(name = "NCR", nullable = false)
    private Group group;

    @Column(nullable = false)
    private LocalDate enrollmentDate;

    @Column(nullable = false, length = 15)
    private String status; // Active, Passed, Failed, Withdrawn
}
