package com.musala.musalatest.Exceptions;

public class DroneUnavailableException extends RuntimeException{

    public DroneUnavailableException() {
    }

    public DroneUnavailableException(String message) {
        super(message);
    }

    public DroneUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public DroneUnavailableException(Throwable cause) {
        super(cause);
    }

    public DroneUnavailableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
