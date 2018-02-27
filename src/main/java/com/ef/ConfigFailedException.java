package com.ef;

public class ConfigFailedException extends Exception {

    public ConfigFailedException(String message) {
        super(message);
    }

    public ConfigFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
