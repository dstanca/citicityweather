package com.citi.cityweatherapi.service;

import com.citi.cityweatherapi.model.dto.CityWeatherResponseDTO;

public interface CityWeatherService {

    CityWeatherResponseDTO getTemperature(String cityName) throws Exception;

}
