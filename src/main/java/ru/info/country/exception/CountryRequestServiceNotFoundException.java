package ru.info.country.exception;

public class CountryRequestServiceNotFoundException extends RuntimeException {
    public CountryRequestServiceNotFoundException() {
        super("Данная страна не найдена по запросу.");
    }
}
