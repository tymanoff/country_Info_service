package ru.info.country.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDto {
    private String code;
    private String description;
}
