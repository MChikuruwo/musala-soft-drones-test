package com.musala.musalatest.Exceptions;

public class DronesUnavailableException extends RuntimeException{

    public DronesUnavailableException() {
    }

    public DronesUnavailableException(String message) {
        super(message);
    }
}
