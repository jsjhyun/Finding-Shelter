package com.example.demo.shelter.repository;

import com.example.demo.shelter.entity.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
