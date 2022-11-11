package ru.kpfu.itis.gnt.exceptions;

import java.util.function.Supplier;

public class EmptyResultDbException extends DBException implements Supplier<DBException> {

    public EmptyResultDbException(){
        super();
    }

    public EmptyResultDbException(String message) {
        super(message);
    }

    public EmptyResultDbException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public DBException get() {
        return this;
    }
}

