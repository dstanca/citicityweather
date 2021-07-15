package com.citi.cityweatherapi.external.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class LocationConsolidatedWeatherResponseDTO implements Serializable {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicable_date;
    private Double min_temp;
    private Double max_temp;
    private Double the_temp;
}
