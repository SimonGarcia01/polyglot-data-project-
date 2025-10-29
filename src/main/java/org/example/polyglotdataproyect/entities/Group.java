package org.example.polyglotdataproyect.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GROUPS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @Column(length = 10)
    private String ncr;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false, length = 6)
    private String semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_code", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Employee professor;
}
