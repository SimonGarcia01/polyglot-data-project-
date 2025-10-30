package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Integer> {
}
