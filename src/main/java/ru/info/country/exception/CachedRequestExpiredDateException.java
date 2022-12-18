package ru.info.country.exception;

import ru.info.country.dto.RequestDto;

import java.text.MessageFormat;

public class CachedRequestExpiredDateException extends RuntimeException {
    public CachedRequestExpiredDateException(RequestDto requestDto) {
        super(MessageFormat.format("В кеше устаревшые данные. необходимо выполнить платный запрос: {}.", requestDto.getService()));
    }
}
