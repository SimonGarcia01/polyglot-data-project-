package org.example.polyglotdataproyect.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "AREAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Area {

    @Id
    private Integer code;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "faculty_code", nullable = false)
    private Faculty faculty;

    @OneToOne
    @JoinColumn(name = "coordinator_id", unique = true, nullable = false)
    private Employee coordinator;

    @JsonIgnore
    @OneToMany(mappedBy = "area",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Program> programs;
}
