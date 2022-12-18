package ru.info.country.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestDto {
    private String service;
    private Integer version;
    private Boolean cached;
    private JsonNode request;
}
