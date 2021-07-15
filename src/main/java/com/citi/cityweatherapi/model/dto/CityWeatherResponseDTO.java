package com.citi.cityweatherapi.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CityWeatherResponseDTO {

    private String cityName;
    private Double tempCelsius;
    private Double tempFarenheits;

}
