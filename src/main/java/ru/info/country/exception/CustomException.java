package ru.info.country.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private final String service;
    private final Integer version;

    public CustomException(String service, Integer version, Throwable cause) {
        super(cause);
        this.service = service;
        this.version = version;
    }
}
