package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.SensorRequest;
import com.energybox.backendcodingchallenge.jpa.entity.LastReading;
import com.energybox.backendcodingchallenge.jpa.entity.Sensor;
import com.energybox.backendcodingchallenge.service.EnergyBoxService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping( value = "/api/v1/sensors" )
@AllArgsConstructor
public class SensorController {

    EnergyBoxService energyBoxService;

    @ApiOperation( value = "Create a Sensor", response = SensorRequest.class )
    @RequestMapping( value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public com.energybox.backendcodingchallenge.jpa.entity.Sensor createSensor(
            @RequestBody SensorRequest sensor
    ) throws IOException, InterruptedException {

            Sensor response = energyBoxService.createSensor(new com.energybox.backendcodingchallenge.
                    jpa.entity.Sensor(sensor.getType(),null,
                    Collections.singletonList(new LastReading(sensor.getLastReadingRequest().getTimestamp(),sensor.getLastReadingRequest().getValue()))));

        return response;
    }

    @ApiOperation( value = "Get all Sensors", response = List.class )
    @RequestMapping( value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public List<Sensor> getAllSensors() throws IOException, InterruptedException {
        return energyBoxService.getAllSensors();
    }

    @ApiOperation( value = "Get Last Readings by Sensor Type", response = List.class )
    @RequestMapping( value = "/lastreadings/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public List<LastReading> getLastReadings(@RequestParam("type") String type) throws Throwable {
        return energyBoxService.getLastReadingsBySensor(type);
    }

    @ApiOperation( value = "Get all Sensors for Gateway", response = List.class )
    @RequestMapping(value="/gateway/{gateway}",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public List<Sensor> getSensorsByGateway(@PathVariable(value = "gateway") String gateway) throws Throwable {
        return energyBoxService.getSensorsByGateway(gateway);
    }

    @ApiOperation( value = "Get Sensor by Type", response = Sensor.class )
    @RequestMapping( value = "/{type}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public Sensor getSensorByType(@PathVariable("type") String type) throws Throwable {
        return energyBoxService.getSensorByType(type);
    }


}
