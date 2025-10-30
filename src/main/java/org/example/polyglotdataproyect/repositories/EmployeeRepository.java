package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
