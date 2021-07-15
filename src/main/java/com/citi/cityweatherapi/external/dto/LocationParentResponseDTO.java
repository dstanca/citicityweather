package com.citi.cityweatherapi.external.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class LocationParentResponseDTO implements Serializable {
    private String title;
    private String slug;
    private String url;
    private Long crawl_rate;
}
