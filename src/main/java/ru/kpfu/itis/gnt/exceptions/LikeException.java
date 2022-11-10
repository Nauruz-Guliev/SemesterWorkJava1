package ru.kpfu.itis.gnt.exceptions;

import java.util.function.Supplier;

public class LikeException extends Exception implements Supplier<LikeException> {

    public LikeException() {
        super();
    }

    public LikeException(String message) {
        super(message);
    }

    public LikeException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public LikeException get() {
        return this;
    }
}