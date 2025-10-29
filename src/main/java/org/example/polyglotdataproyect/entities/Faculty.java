package org.example.polyglotdataproyect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "FACULTIES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Faculty {

    @Id
    private Integer code;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 15)
    private String location;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "dean_id", unique = true)
    private Employee dean;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees;

    @JsonIgnore
    @OneToMany(mappedBy = "faculty",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Area> areas;
}
