package com.citi.cityweatherapi.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
public class ErrorResponseDTO {
    private HttpStatus httpStatus;
    private String message;
}
