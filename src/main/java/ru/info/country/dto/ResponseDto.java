package ru.info.country.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseDto {
    private String service;
    private Integer version;
    private JsonNode response;
}
