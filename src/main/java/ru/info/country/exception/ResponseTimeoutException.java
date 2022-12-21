package ru.info.country.exception;

public class ResponseTimeoutException extends RuntimeException{
    public ResponseTimeoutException(Throwable cause) {
        super(cause);
    }
}
