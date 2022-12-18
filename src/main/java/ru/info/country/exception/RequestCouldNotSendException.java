package ru.info.country.exception;

public class RequestCouldNotSendException extends RuntimeException{
    public RequestCouldNotSendException(Throwable cause) {
        super(cause);
    }
}
