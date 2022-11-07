package ru.info.country.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CounterJsonProcessingException extends RuntimeException {
    public CounterJsonProcessingException(JsonProcessingException ex) {
        super("В запросе нет параметра isoCode: {}", ex);
    }
}
