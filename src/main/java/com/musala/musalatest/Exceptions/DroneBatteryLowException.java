package com.musala.musalatest.Exceptions;

public class DroneBatteryLowException extends RuntimeException {

    public DroneBatteryLowException() {
    }

    public DroneBatteryLowException(String message) {
        super(message);
    }
}
