package com.example.findingshelter.shelter.repository;

import com.example.findingshelter.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
