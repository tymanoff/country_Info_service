package ru.info.country.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private String service;
    private Integer version;
    private JsonNode response;
    private ErrorDto error;
}
