package org.example.polyglotdataproyect.repositories;

import org.example.polyglotdataproyect.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {
}
