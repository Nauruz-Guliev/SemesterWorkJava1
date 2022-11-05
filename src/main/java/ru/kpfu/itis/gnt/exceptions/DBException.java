package ru.kpfu.itis.gnt.exceptions;

import java.util.function.Supplier;

public class DBException extends Exception implements Supplier<DBException> {

    public DBException(){
        super();
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public DBException get() {
        return this;
    }
}
