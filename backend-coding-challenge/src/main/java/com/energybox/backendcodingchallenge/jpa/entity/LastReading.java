package com.energybox.backendcodingchallenge.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastReading {

    @Id
    @GeneratedValue
    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    public LastReading(LocalDateTime timestamp, Double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    private LocalDateTime timestamp;
    private Double value;

    @TargetNode
    @Relationship(type = "FOR_SENSOR", direction = Relationship.Direction.OUTGOING)
    private Sensor sensor;

}