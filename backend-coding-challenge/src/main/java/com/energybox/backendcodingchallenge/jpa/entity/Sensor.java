package com.energybox.backendcodingchallenge.jpa.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Sensor {

    @Id
    private String type;

    public Sensor(String type, Gateway gateway, List<LastReading> lastReadings) {
        this.type = type;
        this.gateway = gateway;
        this.lastReadings = lastReadings;
    }

    @Relationship(type = "CONNECTED_TO",direction = Relationship.Direction.OUTGOING)
    private Gateway gateway;

    @Relationship(type = "LAST_READING", direction = Relationship.Direction.OUTGOING)
    private List<LastReading> lastReadings;
}