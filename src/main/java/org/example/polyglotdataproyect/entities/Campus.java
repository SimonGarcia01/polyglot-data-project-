package org.example.polyglotdataproyect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "CAMPUSES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campus {

    @Id
    private Integer code;

    @Column(length = 20)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code", nullable = false)
    private City city;

    @JsonIgnore
    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @JsonIgnore
    @OneToMany(mappedBy = "campus",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;
}