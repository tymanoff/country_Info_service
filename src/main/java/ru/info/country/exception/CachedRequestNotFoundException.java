package ru.info.country.exception;

import ru.info.country.dto.RequestDto;

import java.text.MessageFormat;

public class CachedRequestNotFoundException extends RuntimeException {
    public CachedRequestNotFoundException(RequestDto request) {
        super(MessageFormat.format("Кешированный запрос не найден по запросу source: {}", request.getRequest()));
    }
}
