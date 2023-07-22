package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.GatewayRequest;
import com.energybox.backendcodingchallenge.domain.SensorList;
import com.energybox.backendcodingchallenge.jpa.entity.Gateway;
import com.energybox.backendcodingchallenge.jpa.entity.Sensor;
import com.energybox.backendcodingchallenge.service.EnergyBoxService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/api/v1/gateways" )
@AllArgsConstructor
public class GatewayController {

    EnergyBoxService energyBoxService;


    @ApiOperation( value = "Get all Gateways", response = List.class )
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public List<Gateway> getAllGateways(){
        return energyBoxService.getAllGateways();
    }

    @ApiOperation( value = "Create a Gateway", response = Gateway.class )
    @RequestMapping( value = "", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE )
    public Gateway createGateway(
            @RequestBody GatewayRequest gateway){
        return energyBoxService.createGateway(gateway);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation( value = "Assign sensor(s) to Gateway")
    @RequestMapping( value = "/{gateway}", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE )
    public void assignSensors(@RequestBody SensorList sensorList,@ApiParam(value = "Gateway Name")  @PathVariable String gateway){
        energyBoxService.assignSensorsToGateway(gateway,sensorList);
    }

    @ApiOperation( value = "Get Gateway by Sensor", response = Gateway.class )
    @RequestMapping(value="/{sensor}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
    public Gateway getGatewayBySensor(@ApiParam(value = "Sensor Name") @PathVariable(value = "sensor") String sensor) throws Throwable {
        return energyBoxService.getGateWayBySensorType(sensor);
    }

}
