package com.citi.cityweatherapi;

import com.citi.cityweatherapi.controller.CityWeatherController;
import com.citi.cityweatherapi.error.CityNotProvidedException;
import com.citi.cityweatherapi.error.ServiceNotAvailableException;
import com.citi.cityweatherapi.external.dto.LocationConsolidatedWeatherResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationDetailResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationResponseDTO;
import com.citi.cityweatherapi.external.service.SearchCityClientService;
import com.citi.cityweatherapi.external.service.TemperatureClientService;
import com.citi.cityweatherapi.model.dto.CityWeatherResponseDTO;
import com.citi.cityweatherapi.service.CityWeatherService;
import com.citi.cityweatherapi.service.impl.CityWeatherServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityWeatheServiceTest {

    @InjectMocks
    CityWeatherServiceImpl cityWeatherService;

    @Mock
    SearchCityClientService searchCityClientService;

    @Mock
    TemperatureClientService temperatureClientService;


    @Test
    public void testGetTemperature() throws Exception {
        CityWeatherResponseDTO dto = CityWeatherResponseDTO.builder()
                .cityName("")
                .tempCelsius(.0D)
                .tempFarenheits(.0D)
                .build();

        LocationDetailResponseDTO responseDTO = LocationDetailResponseDTO.builder()
                .woeid(1L).build();

        List<LocationDetailResponseDTO> lstLocationDetail = new ArrayList<>();
        lstLocationDetail.add(responseDTO);

        LocationConsolidatedWeatherResponseDTO respDTO = LocationConsolidatedWeatherResponseDTO.builder()
                .applicable_date(LocalDate.now())
                .the_temp(27.D)
                .build();

        List<LocationConsolidatedWeatherResponseDTO> listWeatherResponse = new ArrayList<>();
        listWeatherResponse.add(respDTO);

        LocationResponseDTO consolidatedWeatherResponse = LocationResponseDTO.builder()
                .consolidated_weather(listWeatherResponse)
                .build();

        when(searchCityClientService.getCity(any())).thenReturn(lstLocationDetail);
        when(temperatureClientService.getTemperature(any())).thenReturn(consolidatedWeatherResponse);

        CityWeatherResponseDTO response = cityWeatherService.getTemperature("São Paulo");

        assertEquals(response.getTempCelsius(), 27.D);
    }

    @Test
    public void testGetTemperatureFail() throws Exception {
        Assertions.assertThrows(CityNotProvidedException.class, () -> {
            cityWeatherService.getTemperature("");
        });
    }

    @Test
    public void testGetTemperatureFeignException() throws Exception {

        when(searchCityClientService.getCity(any())).thenThrow(FeignException.class);

        Assertions.assertThrows(ServiceNotAvailableException.class, () -> {
            cityWeatherService.getTemperature("sss");
        });
    }
}