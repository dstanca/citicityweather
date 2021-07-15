package com.citi.cityweatherapi.external.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.citi.cityweatherapi.external.dto.LocationDetailResponseDTO;


@FeignClient(value = "searchclient", url = "${external.service.url}")
public interface SearchClientService {

	static final String WHEATER_SEARCH_ENDPOINT = "/api/location/search";
	
	@RequestMapping(method = RequestMethod.GET, value = WHEATER_SEARCH_ENDPOINT)
	List<LocationDetailResponseDTO> getCity(@RequestParam(value="query") String cityName);
}
