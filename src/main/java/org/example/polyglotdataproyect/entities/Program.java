package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "PROGRAMS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Program {

    @Id
    private Integer code;

    @Column(nullable = false, length = 40)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_code", nullable = false)
    private Area area;

    @OneToMany(mappedBy = "program",  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Program> programs;
}