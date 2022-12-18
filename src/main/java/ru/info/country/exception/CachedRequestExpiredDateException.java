package ru.info.country.exception;

public class CachedRequestExpiredDateException extends RuntimeException {
    public CachedRequestExpiredDateException() {
        super("В кеше устаревшие данные. Необходимо выполнить платный запрос.");
    }
}
