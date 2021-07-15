package com.citi.cityweatherapi.external.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class LocationDetailResponseDTO implements Serializable {
	private String title;
	private String location_type;
	private Long woeid;
	private String latt_long;
}
