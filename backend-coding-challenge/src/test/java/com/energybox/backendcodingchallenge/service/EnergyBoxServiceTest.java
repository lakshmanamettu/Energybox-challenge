package com.energybox.backendcodingchallenge.service;

import com.energybox.backendcodingchallenge.domain.GatewayRequest;
import com.energybox.backendcodingchallenge.exception.NotFoundException;
import com.energybox.backendcodingchallenge.jpa.entity.Gateway;
import com.energybox.backendcodingchallenge.jpa.entity.Sensor;
import com.energybox.backendcodingchallenge.jpa.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.jpa.repository.SensorRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;


@RunWith(MockitoJUnitRunner.class)
public class EnergyBoxServiceTest {

  @Mock
  private SensorRepository sensorRepository;
  @Mock
  private GatewayRepository gatewayRepository;


  EnergyBoxService energyBoxService=null;

  @Before
  public void setUp() {
    energyBoxService = new EnergyBoxService(sensorRepository,gatewayRepository);
  }

  @Test
  public void getAllSensors() {

    Mockito.when(sensorRepository.findAll()).thenReturn(Collections.singletonList(
            new Sensor("humidity", null,null)));
    List<Sensor> sensorList = energyBoxService.getAllSensors();
    Assert.assertFalse(sensorList.isEmpty());
  }

  @Test
  public void getAllGateways() {
    Mockito.when(gatewayRepository.findAll()).thenReturn(Collections.singletonList(
            new Gateway("G1", null)));
    List<Gateway> gateways = energyBoxService.getAllGateways();
    Assert.assertFalse(gateways.isEmpty());
  }

  @Test
  public void getSensorsByGateway_positive() throws Throwable {

    Mockito.when(gatewayRepository.findByName("G2")).thenReturn(Optional.of(new Gateway
            ("G2",Collections.singletonList(new Sensor("co2",null,null)))));
    Assert.assertEquals(energyBoxService.getSensorsByGateway("G2").size(),1);

  }

  @Test
  public void getSensorsByGateway_negative() throws Throwable {
    Mockito.when(gatewayRepository.findByName("G1")).thenReturn(Optional.empty());
    Assert.assertThrows(NotFoundException.class,() -> energyBoxService.getSensorsByGateway("G1") );
  }

  @Test
  public void createSensor() {
    Mockito.when(sensorRepository.save(any())).thenReturn(new Sensor("co2",null,null));
    Assert.assertNotNull(energyBoxService.createSensor(new Sensor()));
  }

  @Test
  public void createGateway() {
    Mockito.when(gatewayRepository.save(any())).thenReturn(new Gateway("G1",null));
    Assert.assertNotNull(energyBoxService.createGateway(new GatewayRequest()));
  }

  @Test
  public void getSensorByType_positive() throws Throwable {
    Mockito.when(sensorRepository.findByType("co2")).thenReturn(Optional.of(new Sensor("co2",null,null)));
    Assert.assertNotNull(energyBoxService.getSensorByType("co2"));
  }

  @Test(expected = NotFoundException.class)
  public void getSensorByType_notFound() throws Throwable {
    Mockito.when(sensorRepository.findByType("co2")).thenReturn(Optional.empty());
    energyBoxService.getSensorByType("co2");
  }

  @Test
  public void getLastReadingsBySensor() {
  }

  @Test
  public void assignSensorsToGateway() {}

  @Test
  public void getGateWayBySensorType() {}
}