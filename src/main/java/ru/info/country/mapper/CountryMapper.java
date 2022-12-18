package ru.info.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.oorsprong.websamples.TCountryCodeAndName;
import org.oorsprong.websamples.TCountryInfo;
import ru.info.country.entity.Country;
import ru.info.country.entity.CountryInfo;

@Mapper
public interface CountryMapper {
    CountryMapper MAPPER = Mappers.getMapper(CountryMapper.class);

    @Mapping(source = "SISOCode", target = "isoCode")
    @Mapping(source = "SName", target = "name")
    @Mapping(source = "SCapitalCity", target = "capitalCity")
    @Mapping(source = "SPhoneCode", target = "phoneCode")
    @Mapping(source = "SContinentCode", target = "continentCode")
    @Mapping(source = "SCurrencyISOCode", target = "currencyISOCode")
    @Mapping(source = "SCountryFlag", target = "countryFlag")
    @Mapping(target = "languages", ignore = true)
    CountryInfo convert(TCountryInfo tCountryInfo);

    @Mapping(source = "SISOCode", target = "isoCode")
    @Mapping(source = "SName", target = "name")
    Country convert(TCountryCodeAndName tCountryCodeAndName);
}
