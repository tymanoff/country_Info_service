package ru.info.country.configuration;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.oorsprong.websamples_countryinfo.CountryInfoService;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SoapTemplateConfig {

    private static final String PATH_COUNTRY_INFO_SERVICE_MOCK_WSDL = "/wsdl/Country_info_mock.wsdl";

    private CountryServiceProperties countryServiceProperties;

    @Bean
    @SneakyThrows
    public CountryInfoServiceSoapType countryInfoService() {
        return new CountryInfoService().getCountryInfoServiceSoap();
    }

}
