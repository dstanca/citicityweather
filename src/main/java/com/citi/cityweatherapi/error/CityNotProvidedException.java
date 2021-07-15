package com.citi.cityweatherapi.error;

public class CityNotProvidedException extends Exception {
    public CityNotProvidedException(String message) {
        super(message);
    }
}
