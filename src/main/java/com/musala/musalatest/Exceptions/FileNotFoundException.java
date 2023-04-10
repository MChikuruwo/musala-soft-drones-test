package com.musala.musalatest.Exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(final String s) {
        super(s);
    }
}