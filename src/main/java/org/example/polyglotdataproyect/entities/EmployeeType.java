package org.example.polyglotdataproyect.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "EMPLOYEE_TYPES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeType {

    @Id
    @Column(length = 30)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "employeeType", cascade = CascadeType.ALL,  orphanRemoval = true)
    private List<Employee> employees;
}
