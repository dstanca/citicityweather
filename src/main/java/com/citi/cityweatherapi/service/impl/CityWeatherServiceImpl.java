package com.citi.cityweatherapi.service.impl;

import com.citi.cityweatherapi.error.CityNotFoundException;
import com.citi.cityweatherapi.error.CityNotProvidedException;
import com.citi.cityweatherapi.error.ServiceNotAvailableException;
import com.citi.cityweatherapi.error.TemperatureNotFoundException;
import com.citi.cityweatherapi.external.dto.LocationConsolidatedWeatherResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationDetailResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationResponseDTO;
import com.citi.cityweatherapi.external.service.SearchCityClientService;
import com.citi.cityweatherapi.external.service.TemperatureClientService;
import com.citi.cityweatherapi.model.dto.CityWeatherResponseDTO;
import com.citi.cityweatherapi.service.CityWeatherService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Objects;

@Slf4j
public class CityWeatherServiceImpl implements CityWeatherService {

    private static final int NUMBER_32 = 32;
    private static final int NUMBER_5 = 5;
    private static final int NUMBER_9 = 9;

    @Autowired
    SearchCityClientService searchCityClientService;

    @Autowired
    TemperatureClientService temperatureClientService;

    //    @Transactional(readOnly = true)
    @Override
    public CityWeatherResponseDTO getTemperature(String cityName) throws Exception {

        if (Objects.isNull(cityName) || cityName.trim().isEmpty()) {
            throw new CityNotProvidedException("You must provide a City name.");
        }

        LocationDetailResponseDTO locationDetailResponseDTO = null;
        LocationConsolidatedWeatherResponseDTO locationConsolidatedWeatherResponseDTO =
                null;
        try {
            locationDetailResponseDTO = searchCityClientService.getCity(cityName).stream()
                    .findFirst()
                    .orElseThrow(() -> new CityNotFoundException("City not found - " + cityName));

            LocationResponseDTO temperature = temperatureClientService.getTemperature(locationDetailResponseDTO.getWoeid());

            locationConsolidatedWeatherResponseDTO = temperature.getConsolidated_weather().stream()
                    .sorted(Comparator.comparing(LocationConsolidatedWeatherResponseDTO::getApplicable_date))
                    .findFirst()
                    .orElseThrow(() -> new TemperatureNotFoundException("Temperature not found for City - " + cityName));
        } catch (FeignException e) {
            log.error("Error whilst calling external service: {}", e);
            throw new ServiceNotAvailableException(e.contentUTF8());
        }

        return CityWeatherResponseDTO.builder()
                .cityName(locationDetailResponseDTO.getTitle())
                .tempCelsius(locationConsolidatedWeatherResponseDTO.getThe_temp())
                .tempFarenheits(convertToFarenheit(locationConsolidatedWeatherResponseDTO.getThe_temp()))
                .build();
    }

    private Double convertToFarenheit(Double celsius) {
        return ((celsius * NUMBER_9) / NUMBER_5) + NUMBER_32;
    }

}
