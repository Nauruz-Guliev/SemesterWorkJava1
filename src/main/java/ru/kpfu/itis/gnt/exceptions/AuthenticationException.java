package ru.kpfu.itis.gnt.exceptions;

public class AuthenticationException extends Exception{
    public AuthenticationException() {
        super();
    }
    public AuthenticationException(String message) {
        super(message);
    }
    public AuthenticationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
