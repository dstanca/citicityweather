package com.citi.cityweatherapi;

import com.citi.cityweatherapi.controller.CityWeatherController;
import com.citi.cityweatherapi.error.ServiceNotAvailableException;
import com.citi.cityweatherapi.error.TemperatureNotFoundException;
import com.citi.cityweatherapi.external.dto.LocationConsolidatedWeatherResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationDetailResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationResponseDTO;
import com.citi.cityweatherapi.external.service.SearchCityClientService;
import com.citi.cityweatherapi.external.service.TemperatureClientService;
import com.citi.cityweatherapi.model.dto.CityWeatherResponseDTO;
import com.citi.cityweatherapi.service.CityWeatherService;
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
public class CityWeatherControllerTest {

    @InjectMocks
    CityWeatherController cityWeatherController;

    @Mock
    CityWeatherService cityWeatherService;

    @Mock
    SearchCityClientService searchCityClientService;

    @Mock
    TemperatureClientService temperatureClientService;

    @Test
    public void testHealth() throws Exception {
        assertEquals(cityWeatherController.health(), "Server is up");
    }

    @Test
    public void testLatestTemperature() throws Exception {
        CityWeatherResponseDTO dto = CityWeatherResponseDTO.builder()
                .cityName("")
                .tempCelsius(.0D)
                .tempFarenheits(.0D)
                .build();

        when(cityWeatherService.getTemperature(any())).thenReturn(dto);

        ResponseEntity<CityWeatherResponseDTO> response = cityWeatherController.latestTemperature("São Paulo");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testsearchCity() throws Exception {
        LocationDetailResponseDTO responseDTO = LocationDetailResponseDTO.builder()
                .woeid(1L).build();

        List<LocationDetailResponseDTO> lstLocationDetail = new ArrayList<>();
        lstLocationDetail.add(responseDTO);

        when(searchCityClientService.getCity(any())).thenReturn(lstLocationDetail);

        ResponseEntity<List<LocationDetailResponseDTO>> response = cityWeatherController.searchCity("São Paulo");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testsearchTemperature() throws Exception {
        LocationConsolidatedWeatherResponseDTO respDTO = LocationConsolidatedWeatherResponseDTO.builder()
                .applicable_date(LocalDate.now())
                .the_temp(27.D)
                .build();

        List<LocationConsolidatedWeatherResponseDTO> listWeatherResponse = new ArrayList<>();
        listWeatherResponse.add(respDTO);

        LocationResponseDTO consolidatedWeatherResponse = LocationResponseDTO.builder()
                .consolidated_weather(listWeatherResponse)
                .build();

        when(temperatureClientService.getTemperature(any())).thenReturn(consolidatedWeatherResponse);

        ResponseEntity<LocationResponseDTO> response = cityWeatherController.searchTemperature(1L);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testLatestTemperatureFailTemperatureNotFoundException() throws Exception {
        when(cityWeatherService.getTemperature(any())).thenThrow(TemperatureNotFoundException.class);

        ResponseEntity<CityWeatherResponseDTO> response = cityWeatherController.latestTemperature("São Paulo");

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testLatestTemperatureFailServiceNotAvailableException() throws Exception {
        when(cityWeatherService.getTemperature(any())).thenThrow(ServiceNotAvailableException.class);

        ResponseEntity<CityWeatherResponseDTO> response = cityWeatherController.latestTemperature("São Paulo");

        assertEquals(response.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Test
    public void testLatestTemperatureFailException() throws Exception {
        when(cityWeatherService.getTemperature(any())).thenThrow(Exception.class);

        ResponseEntity<CityWeatherResponseDTO> response = cityWeatherController.latestTemperature("São Paulo");

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}