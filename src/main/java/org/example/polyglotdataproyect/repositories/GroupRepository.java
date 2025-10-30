package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,String> {
}
