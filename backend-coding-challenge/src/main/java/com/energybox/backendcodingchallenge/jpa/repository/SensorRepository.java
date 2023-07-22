package com.energybox.backendcodingchallenge.jpa.repository;

import com.energybox.backendcodingchallenge.jpa.entity.Sensor;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;


public interface SensorRepository extends Neo4jRepository<Sensor, String> {

   Optional<Sensor> findByType(String type);
   List<Sensor> findAll();
}
