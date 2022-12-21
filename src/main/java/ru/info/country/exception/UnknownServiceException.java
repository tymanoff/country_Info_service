package ru.info.country.exception;

public class UnknownServiceException extends RuntimeException{
    public UnknownServiceException(Throwable cause) {
        super(cause);
    }
}
