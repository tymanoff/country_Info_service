package ru.info.country.exception;

public class CachedRequestNotFoundException extends RuntimeException {
    public CachedRequestNotFoundException() {
        super("Кешированный запрос не найден по параметрам.");
    }
}
