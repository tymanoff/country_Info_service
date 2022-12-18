package ru.info.country.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CountryInfo {
    private String isoCode;
    private String name;
    private String capitalCity;
    private String phoneCode;
    private String continentCode;
    private String currencyISOCode;
    private String countryFlag;
    private List<Language> languages;
}
