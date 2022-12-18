package ru.info.country.exception;

import ru.info.country.dto.RequestDto;

import java.text.MessageFormat;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(RequestDto request) {
        super(MessageFormat.format("Такой сервис не найден:{}", request.getService()));
    }
}
