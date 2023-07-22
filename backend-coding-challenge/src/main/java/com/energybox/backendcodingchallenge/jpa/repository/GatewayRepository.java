package com.energybox.backendcodingchallenge.jpa.repository;

import com.energybox.backendcodingchallenge.jpa.entity.Gateway;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GatewayRepository extends Neo4jRepository<Gateway, String> {

    Optional<Gateway> findByName(String name);
    List<Gateway> findAll();
    @Query("MATCH(g:Gateway) <- [:CONNECTED_TO] - (s:Sensor) WHERE s.type=$sensorType RETURN g")
    Optional<Gateway> findGatewayWithElectricalSensors(@Param("sensorType") String sensorType);

}