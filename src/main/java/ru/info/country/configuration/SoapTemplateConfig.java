package ru.info.country.configuration;

import org.oorsprong.websamples_countryinfo.CountryInfoService;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapTemplateConfig {

    @Bean
    public CountryInfoServiceSoapType countryInfoServiceSoap() {
        return new CountryInfoService().getCountryInfoServiceSoap();
    }
}
