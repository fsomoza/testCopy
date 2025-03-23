package com.hackathon.inditex.repositories;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    Optional<Center> findByCoordinates(Double latitude, Double longitude);
}