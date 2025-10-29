package org.example.polyglotdataproyect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "SUBJECTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @Column(length = 10)
    private String code;

    @Column(nullable = false, length = 30)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_code", nullable = false)
    private Program program;

    @JsonIgnore
    @OneToMany(mappedBy = "subject",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;
}
