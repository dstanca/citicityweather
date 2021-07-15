package com.citi.cityweatherapi.controller;

import com.citi.cityweatherapi.error.CityNotFoundException;
import com.citi.cityweatherapi.error.CityNotProvidedException;
import com.citi.cityweatherapi.error.ServiceNotAvailableException;
import com.citi.cityweatherapi.error.TemperatureNotFoundException;
import com.citi.cityweatherapi.external.dto.LocationDetailResponseDTO;
import com.citi.cityweatherapi.external.dto.LocationResponseDTO;
import com.citi.cityweatherapi.external.service.SearchCityClientService;
import com.citi.cityweatherapi.external.service.TemperatureClientService;
import com.citi.cityweatherapi.model.dto.CityWeatherResponseDTO;
import com.citi.cityweatherapi.model.dto.ErrorResponseDTO;
import com.citi.cityweatherapi.service.CityWeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@Api(value = "Get latest Cities' temperatures in ºC and ºF")
public class CityWeatherController {

    @Autowired
    SearchCityClientService searchCityClientService;

    @Autowired
    TemperatureClientService temperatureClientService;

    @Autowired
    CityWeatherService cityWeatherService;

    @ApiOperation("API Health Check")
    @GetMapping("/health")
    public String health() {
        return "Server is up";
    }

    @ApiOperation("Invoke external service to get the City Id.")
    @GetMapping("/searchcity/{cityName}")
    public ResponseEntity<List<LocationDetailResponseDTO>> searchCity(@PathVariable String cityName) {
        return new ResponseEntity<List<LocationDetailResponseDTO>>(
                searchCityClientService.getCity(cityName),
                HttpStatus.OK);
    }

    @ApiOperation("Invoke external service to get the City's temperatures.")
    @GetMapping("/searchtemperature/{woeid}")
    public ResponseEntity<LocationResponseDTO> searchTemperature(@PathVariable Long woeid) {
        return new ResponseEntity<LocationResponseDTO>(
                temperatureClientService.getTemperature(woeid),
                HttpStatus.OK);
    }

    @ApiOperation("Get the latest City temperatures based on City Name.")
    @GetMapping("/latesttemperature/{cityName}")
    public ResponseEntity<CityWeatherResponseDTO> latestTemperature(@PathVariable String cityName) {

        CityWeatherResponseDTO cityWeatherResponseDTO = CityWeatherResponseDTO.builder().build();

        try {
            cityWeatherResponseDTO = cityWeatherService.getTemperature(cityName);
        } catch (CityNotProvidedException | CityNotFoundException | TemperatureNotFoundException e) {
            return new ResponseEntity(buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (ServiceNotAvailableException e) {
            return new ResponseEntity(buildErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            return new ResponseEntity(buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<CityWeatherResponseDTO>(
                cityWeatherResponseDTO,
                HttpStatus.OK);
    }

    private ErrorResponseDTO buildErrorResponse(HttpStatus httpStatus, String msg) {
        return ErrorResponseDTO.builder()
                .httpStatus(httpStatus)
                .message(msg)
                .build();
    }

}
