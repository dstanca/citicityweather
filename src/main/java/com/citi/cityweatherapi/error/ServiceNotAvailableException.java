package com.citi.cityweatherapi.error;

public class ServiceNotAvailableException extends Exception {
    public ServiceNotAvailableException(String message) {
        super(message);
    }
}
