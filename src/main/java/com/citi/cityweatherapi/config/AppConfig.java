package com.citi.cityweatherapi.config;

import com.citi.cityweatherapi.service.CityWeatherService;
import com.citi.cityweatherapi.service.impl.CityWeatherServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CityWeatherService cityWeatherService() {
        return new CityWeatherServiceImpl();
    }
}