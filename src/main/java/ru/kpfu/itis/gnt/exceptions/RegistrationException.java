package ru.kpfu.itis.gnt.exceptions;

import java.util.function.Supplier;

public class RegistrationException  extends Exception implements Supplier<RegistrationException> {

    public RegistrationException() {
        super();
    }

    public RegistrationException(String message) {
        super(message);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public RegistrationException get() {
        return this;

    }
}