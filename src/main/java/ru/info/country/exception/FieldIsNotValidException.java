package ru.info.country.exception;

public class FieldIsNotValidException extends RuntimeException {
    public FieldIsNotValidException() {
        super("Ошибка валидации параметра страны");
    }
}
