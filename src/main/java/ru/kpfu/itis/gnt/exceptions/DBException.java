package ru.kpfu.itis.gnt.exceptions;

public class DBException extends Exception{

    public DBException(){
        super();
    }

    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }
}
