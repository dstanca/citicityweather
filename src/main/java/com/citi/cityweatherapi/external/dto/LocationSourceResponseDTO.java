package com.citi.cityweatherapi.external.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class LocationSourceResponseDTO implements Serializable {
    private String title;
    private String location_type;
    private Long woeid;
    private String latt_long;
}
