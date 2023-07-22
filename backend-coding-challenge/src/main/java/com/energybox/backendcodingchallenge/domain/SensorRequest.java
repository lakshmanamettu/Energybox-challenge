package com.energybox.backendcodingchallenge.domain;


import lombok.Data;

@Data
public class SensorRequest {

    String type;
    LastReadingRequest lastReadingRequest;

}
