package com.musala.musalatest.Exceptions;

public class MedicationAlreadyExistsException extends RuntimeException {

    public MedicationAlreadyExistsException() {
    }

    public MedicationAlreadyExistsException(String message) {
        super(message);
    }
}
