package com.hackathon.inditex.repositories;

import com.hackathon.inditex.Entities.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {
    @Query("SELECT c FROM Center c WHERE c.coordinates.latitude = :latitude AND c.coordinates.longitude = :longitude")
    Optional<Center> findByCoordinates(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
}