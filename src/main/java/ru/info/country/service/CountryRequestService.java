package ru.info.country.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.oorsprong.websamples.TCountryInfo;
import org.oorsprong.websamples_countryinfo.CountryInfoServiceSoapType;
import org.springframework.stereotype.Service;
import ru.info.country.country.CountryDetailsRequestNode;
import ru.info.country.dto.RequestDto;
import ru.info.country.dto.ResponseDto;
import ru.info.country.entity.Country;
import ru.info.country.entity.CountryInfo;
import ru.info.country.entity.Language;
import ru.info.country.exception.CounterJsonProcessingException;
import ru.info.country.exception.FieldIsNotValidException;
import ru.info.country.mapper.CountryMapper;
import ru.info.country.mapper.LanguageMapper;

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

        ResponseDto countryInfo;
        String isoCode = countryDetailsRequestNode.getIsoCode();
        if (isoCode.equals(COUNTRY_ALL)) {
            countryInfo = getListAllCountry(request);
        } else {
            countryInfo = getCountryInfo(request, isoCode);
        }

        return countryInfo;
    }

    private ResponseDto getCountryInfo(RequestDto request, String isoCode) {
        if (StringUtils.isBlank(isoCode) || isoCode.length() != 2) {
            throw new FieldIsNotValidException(request);
        }

        TCountryInfo tCountryInfo = countryInfoServiceSoapType.fullCountryInfo(isoCode);
        CountryInfo countryInfo = CountryMapper.MAPPER.convert(tCountryInfo);
        List<Language> languages = tCountryInfo.getLanguages().getTLanguage()
                .stream()
                .map(LanguageMapper.MAPPER::convert)
                .toList();
        countryInfo.setLanguages(languages);
        JsonNode node = objectMapper.valueToTree(countryInfo);
        return getResponseDto(request, node);
    }

    public ResponseDto getListAllCountry(RequestDto request) {
        List<Country> countries = countryInfoServiceSoapType
                .listOfCountryNamesByCode()
                .getTCountryCodeAndName()
                .stream()
                .map(CountryMapper.MAPPER::convert)
                .toList();
        JsonNode node = objectMapper.valueToTree(countries);
        return getResponseDto(request, node);
    }

    private ResponseDto getResponseDto(RequestDto request, JsonNode countryInfoNode) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setService(request.getService());
        responseDto.setVersion(request.getVersion());
        responseDto.setResponse(countryInfoNode);
        return responseDto;
    }
}
