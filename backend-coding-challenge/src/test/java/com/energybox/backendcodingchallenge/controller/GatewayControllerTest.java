package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.GatewayRequest;
import com.energybox.backendcodingchallenge.jpa.entity.Gateway;
import com.energybox.backendcodingchallenge.jpa.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.jpa.repository.SensorRepository;
import com.energybox.backendcodingchallenge.service.EnergyBoxService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GatewayController.class,excludeAutoConfiguration = {Neo4jAutoConfiguration.class})
public class GatewayControllerTest {

  @MockBean
  SensorRepository sensorRepository;
  @MockBean
  GatewayRepository gatewayRepository;
  @MockBean
  EnergyBoxService energyBoxService;

  @Autowired
  MockMvc mockMvc;

 // private static Neo4j neo4j;

  /*@BeforeAll
  static void startEmbeddedNeo4j() throws URISyntaxException {

    // We need the directory containing the APOC jar, otherwise all APOC procedures must be loaded manually.
    // While the intuitive idea might be not having APOC on the class path at all in that case and just dump
    // it into the plugin directory, it doesn't work as APOC needs some extension factories to work with
    // and those are not loaded from the plugin unless it's part of the original class loader that loaded neo.
    // If you know which methods you're a gonna use, you can configure them manually instead.
    var pluginDirContainingApocJar = new File(
            GatewayControllerTest.class.getProtectionDomain().getCodeSource().getLocation().toURI())
            .getParentFile().toPath();
    neo4j = Neo4jBuilders
            .newInProcessBuilder()
            .withDisabledServer()
            //.withFixture("CREATE (m:Movie {title: 'Fight Club'}) RETURN m")
            .withConfig(GraphDatabaseSettings.plugin_dir, pluginDirContainingApocJar)
            .withConfig(GraphDatabaseSettings.procedure_unrestricted, List.of("apoc.*"))
            .build();

  }

  @DynamicPropertySource
  static void neo4jProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.neo4j.uri", neo4j::boltURI);
    registry.add("spring.data.neo4j.username", () -> "neo4j");
    registry.add("spring.data.neo4j.password", () -> "123456");
  }*/

  @Test
  public void getAllGateways() throws Exception {

    Mockito.when(energyBoxService.getAllGateways()).thenReturn(Collections.singletonList(new Gateway("G1",null)));
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/gateways")
    .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());


  }

  @Test
  public void createGateway() throws Exception {
    Mockito.when(energyBoxService.createGateway(any())).thenReturn(new Gateway("G1",null));
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/gateways")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(new GatewayRequest())))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  public void assignSensors() throws Exception {
    doNothing().when(energyBoxService).assignSensorsToGateway(anyString(),any());
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/gateways/G1")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(new GatewayRequest())))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
  }

  @Test
  public void getGatewayBySensor() throws Throwable {
    Mockito.when(energyBoxService.getGateWayBySensorType(anyString())).thenReturn(new Gateway("G1",null));
    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/gateways/co2")
            .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(new GatewayRequest())))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

  }

 /* @AfterAll
  static void stopEmbeddedNeo4j() {
    neo4j.close();
  }*/
}