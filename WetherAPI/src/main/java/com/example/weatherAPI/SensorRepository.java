package com.example.weatherAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,Long> {
    List<Sensor> findAllByIsActive(Boolean isActive);

    Optional<Sensor> findByKey(String key);

    Optional<Sensor> findByName(String name);


}
