package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.EmployeeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTypeRepository extends JpaRepository<EmployeeType, String> {
}
