package ru.kpfu.itis.gnt.exceptions;

import java.util.function.Supplier;

public class WrongParameterException extends Exception implements Supplier<WrongParameterException> {

    public WrongParameterException() {
        super();
    }

    public WrongParameterException(String message) {
        super(message);
    }

    public WrongParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public WrongParameterException get() {
        return this;
    }
}