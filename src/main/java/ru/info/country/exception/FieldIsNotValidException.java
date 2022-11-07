package ru.info.country.exception;

import ru.info.country.dto.RequestDto;

import java.text.MessageFormat;

public class FieldIsNotValidException extends RuntimeException {
    public FieldIsNotValidException(RequestDto requestDto) {
        super(MessageFormat.format("Ошибка валидации isoCode: {}", requestDto.getRequest()));
    }
}
