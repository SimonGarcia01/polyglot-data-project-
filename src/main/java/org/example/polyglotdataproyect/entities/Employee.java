package org.example.polyglotdataproyect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "EMPLOYEES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @Column(length = 15)
    private String id;

    @Column(nullable = false, length = 30)
    private String firstName;

    @Column(nullable = false, length = 30)
    private String lastName;

    @Column(nullable = false, length = 30)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_type", nullable = false)
    private ContractType contractType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_type", nullable = false)
    private EmployeeType employeeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_code", nullable = false)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_code", nullable = false)
    private Campus campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_place_code", nullable = false)
    private City birthPlace;

    @JsonIgnore
    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group> groups;
}
