package ru.info.country.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.oorsprong.websamples.TCountryInfo;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.stereotype.Service;
import ru.info.country.country.CountryDetailsRequestNode;
import ru.info.country.dto.RequestDto;
import ru.info.country.dto.ResponseDto;
import ru.info.country.entity.Country;
import ru.info.country.entity.CountryInfo;
import ru.info.country.entity.Language;
import ru.info.country.exception.CounterJsonProcessingException;
import ru.info.country.exception.FieldIsNotValidException;
import ru.info.country.exception.UnknownServiceException;
import ru.info.country.exception.RequestCouldNotSendException;
import ru.info.country.exception.ResponseTimeoutException;
import ru.info.country.mapper.CountryMapper;
import ru.info.country.mapper.LanguageMapper;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryRequestService {

    private static final String COUNTRY_ALL = "all";

    private final ObjectMapper objectMapper;
    private final CountryInfoServiceSoapType countryInfoServiceSoapType;

    public ResponseDto getCountryInfo(RequestDto request) {
        JsonNode node = request.getRequest();

        CountryDetailsRequestNode countryDetailsRequestNode;
        try {
            countryDetailsRequestNode =
                    objectMapper.treeToValue(node, CountryDetailsRequestNode.class);
        } catch (JsonProcessingException ex) {
            throw new CounterJsonProcessingException(ex);
        }

        JsonNode country;
        String isoCode = countryDetailsRequestNode.getIsoCode();
        if (StringUtils.isBlank(isoCode)) {
            throw new FieldIsNotValidException();
        } else if (isoCode.equals(COUNTRY_ALL)) {
            country = getListAllCountry();
        } else {
            country = getCountryInfo(isoCode);
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setService(request.getService());
        responseDto.setVersion(request.getVersion());
        responseDto.setResponse(country);
        return responseDto;
    }

    private JsonNode getCountryInfo(String isoCode) {
        if (isoCode.length() != 2) {
            throw new FieldIsNotValidException();
        }

        TCountryInfo tCountryInfo;
        try {
            tCountryInfo = countryInfoServiceSoapType.fullCountryInfo(isoCode);
        } catch (WebServerException ex) {
            extracted(ex);

            throw new UnknownServiceException(ex);
        }
        CountryInfo countryInfo = CountryMapper.MAPPER.convert(tCountryInfo);
        List<Language> languages = tCountryInfo.getLanguages().getTLanguage()
                .stream()
                .map(LanguageMapper.MAPPER::convert)
                .toList();
        countryInfo.setLanguages(languages);
        return objectMapper.valueToTree(countryInfo);
    }

    public JsonNode getListAllCountry() {
        List<Country> countries;
        try {
            countries = countryInfoServiceSoapType
                    .listOfCountryNamesByCode()
                    .getTCountryCodeAndName()
                    .stream()
                    .map(CountryMapper.MAPPER::convert)
                    .toList();
        } catch (WebServerException ex) {
            extracted(ex);

            throw new UnknownServiceException(ex);
        }
        return objectMapper.valueToTree(countries);
    }

    private void extracted(WebServerException ex) {
        if (ex.getCause() instanceof SocketTimeoutException) {
            throw new ResponseTimeoutException(ex);
        }
        if (ex.getCause() instanceof ConnectException) {
            throw new RequestCouldNotSendException(ex);
        }
    }
}
