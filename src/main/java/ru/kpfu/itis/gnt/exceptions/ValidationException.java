package ru.kpfu.itis.gnt.exceptions;

import java.util.function.Supplier;

public class ValidationException extends Exception implements Supplier<ValidationException> {

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public ValidationException get() {
        return this;
    }
}