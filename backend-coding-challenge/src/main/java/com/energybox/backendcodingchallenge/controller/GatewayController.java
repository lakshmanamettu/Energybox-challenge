package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.service.GatewayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping( value = "/gateways" )
public class GatewayController {

    private final GatewayService service;

    public GatewayController( GatewayService service ) {
        this.service = service;
    }

    @ApiOperation( value = "create a gateway", response = Gateway.class )
    @RequestMapping( value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Object> create(
            @RequestBody Object comment
    ) throws IOException, InterruptedException {

        return new ResponseEntity<>(  HttpStatus.OK );
    }
}
