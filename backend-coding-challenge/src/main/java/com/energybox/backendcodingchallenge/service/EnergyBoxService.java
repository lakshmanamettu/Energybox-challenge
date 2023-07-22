package com.energybox.backendcodingchallenge.service;

import com.energybox.backendcodingchallenge.domain.GatewayRequest;
import com.energybox.backendcodingchallenge.domain.SensorList;
import com.energybox.backendcodingchallenge.exception.NotFoundException;
import com.energybox.backendcodingchallenge.jpa.entity.Gateway;
import com.energybox.backendcodingchallenge.jpa.entity.LastReading;
import com.energybox.backendcodingchallenge.jpa.entity.Sensor;
import com.energybox.backendcodingchallenge.jpa.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.jpa.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EnergyBoxService {

    private final SensorRepository sensorRepository;
    private final GatewayRepository gatewayRepository;

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    public List<Gateway> getAllGateways() {
        return gatewayRepository.findAll();
    }



    public List<Sensor> getSensorsByGateway(String name) throws Throwable {
        return gatewayRepository.findByName(name)
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("Sensors Not Found for Gateway Type:"+name)).getSensors();

    }

    public Sensor createSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Gateway createGateway(GatewayRequest gateway) {
        return gatewayRepository.save(new Gateway(gateway.getName(),null));
    }

    public Sensor getSensorByType(String type) throws Throwable {
        return sensorRepository.findByType(type)
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("Sensor Not Found for type:"+type));
    }

    public List<LastReading> getLastReadingsBySensor(String type) throws Throwable {
       return sensorRepository.findByType(type)
               .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("Sensor Not Found for type:"+type)).getLastReadings();
    }

    public void assignSensorsToGateway(String gateway, SensorList sensorList) {


       List<Sensor> sensors =  sensorList.getSensorList().stream().map(s -> sensorRepository.findByType(s).orElse(null))
                .filter(Objects::nonNull)
                .filter(s -> null==s.getGateway())
                .collect(Collectors.toList());

        Gateway gateway1 = gatewayRepository.findByName(gateway).orElse(new Gateway(gateway,null));
        gateway1.setSensors(sensors);
        gatewayRepository.save(gateway1);
    }

    public Gateway getGateWayBySensorType(String sensor) throws Throwable {
        return gatewayRepository.findGatewayWithElectricalSensors(sensor)
                .orElseThrow((Supplier<Throwable>) () -> new NotFoundException("No Gateway assigned to Sensor:"+sensor));
    }

}
