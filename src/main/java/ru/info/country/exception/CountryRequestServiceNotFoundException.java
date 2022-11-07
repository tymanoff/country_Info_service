package ru.info.country.exception;

import ru.info.country.dto.RequestDto;

import java.text.MessageFormat;

public class CountryRequestServiceNotFoundException extends RuntimeException {
    public CountryRequestServiceNotFoundException(RequestDto request) {
        super(MessageFormat.format("Данная страна не найдена по запросу source: {}", request.getRequest()));
    }
}
