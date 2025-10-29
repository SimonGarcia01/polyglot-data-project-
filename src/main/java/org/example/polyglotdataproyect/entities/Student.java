package org.example.polyglotdataproyect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "STUDENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(length = 15)
    private String id;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_place_code", nullable = false)
    private City birthPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_code", nullable = false)
    private Campus campus;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollment> enrollments;

    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<User> users;
}
