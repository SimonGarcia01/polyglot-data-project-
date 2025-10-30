package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
