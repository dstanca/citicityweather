package com.citi.cityweatherapi.external.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponseDTO implements Serializable {

    private List<LocationConsolidatedWeatherResponseDTO> consolidated_weather;
}
