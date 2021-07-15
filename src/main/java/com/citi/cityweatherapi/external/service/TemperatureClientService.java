package com.citi.cityweatherapi.external.service;

import com.citi.cityweatherapi.external.dto.LocationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "temperatureclient", url = "${external.service.url}")
public interface TemperatureClientService {

    static final String WHEATER_SEARCH_ENDPOINT = "/api/location/{id}";

    @RequestMapping(method = RequestMethod.GET, value = WHEATER_SEARCH_ENDPOINT)
    LocationResponseDTO getTemperature(@PathVariable(value = "id") Long id);
}
