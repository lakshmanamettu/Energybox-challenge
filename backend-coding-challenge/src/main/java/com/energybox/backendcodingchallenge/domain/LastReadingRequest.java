package com.energybox.backendcodingchallenge.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastReadingRequest {

    private LocalDateTime timestamp;
    private Double value;
}
