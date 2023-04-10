package com.musala.musalatest.Exceptions;

public class MedicationUnavailableException extends RuntimeException{

    public MedicationUnavailableException() {
    }

    public MedicationUnavailableException(String message) {
        super(message);
    }
}
