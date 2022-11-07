package ru.info.country.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.oorsprong.websamples.TCountryCodeAndName;
import org.oorsprong.websamples.TCountryInfo;
import ru.info.country.entity.Country;
import ru.info.country.entity.CountryInfo;
import ru.info.country.entity.Language;

import javax.xml.bind.JAXB;
import java.util.List;
import java.util.Objects;

class CountryMapperTest {

    @Test
    void convertCountryInfo() {
        // given
        TCountryInfo tCountryInfo = unmarshal(TCountryInfo.class);

        // when
        CountryInfo countryInfo = CountryMapper.MAPPER.convert(tCountryInfo);
        List<Language> languages = tCountryInfo.getLanguages().getTLanguage()
                .stream()
                .map(LanguageMapper.MAPPER::convert)
                .toList();

        // then
        // counter validator
        Assertions.assertThat(countryInfo).isNotNull();
        Assertions.assertThat(countryInfo.getIsoCode()).isEqualTo(tCountryInfo.getSISOCode());
        Assertions.assertThat(countryInfo.getName()).isEqualTo(tCountryInfo.getSName());
        Assertions.assertThat(countryInfo.getCapitalCity()).isEqualTo(tCountryInfo.getSCapitalCity());
        Assertions.assertThat(countryInfo.getPhoneCode()).isEqualTo(tCountryInfo.getSPhoneCode());
        Assertions.assertThat(countryInfo.getContinentCode()).isEqualTo(tCountryInfo.getSContinentCode());
        Assertions.assertThat(countryInfo.getCurrencyISOCode()).isEqualTo(tCountryInfo.getSCurrencyISOCode());
        Assertions.assertThat(countryInfo.getCountryFlag()).isEqualTo(tCountryInfo.getSCountryFlag());

        // language validator
        Assertions.assertThat(languages.get(0).getIsoCode())
                .isEqualTo(tCountryInfo.getLanguages().getTLanguage().get(0).getSISOCode());
        Assertions.assertThat(languages.get(0).getName())
                .isEqualTo(tCountryInfo.getLanguages().getTLanguage().get(0).getSName());
    }

    @Test
    void convertCountry() {
        // given
        TCountryCodeAndName tCountryCodeAndName = unmarshal(TCountryCodeAndName.class);

        // when
        Country country = CountryMapper.MAPPER.convert(tCountryCodeAndName);

        // then
        Assertions.assertThat(country).isNotNull();
        Assertions.assertThat(country.getIsoCode()).isEqualTo(tCountryCodeAndName.getSISOCode());
        Assertions.assertThat(country.getName()).isEqualTo(tCountryCodeAndName.getSName());
    }

    @Test
    void testCountryContainingInfoNull() {
        // given
        TCountryInfo tCountryInfo = null;

        // when
        CountryInfo countryInfo = CountryMapper.MAPPER.convert(tCountryInfo);

        // then
        Assertions.assertThat(countryInfo).isNull();
    }

    @Test
    void testCountryContainingNull() {
        // given
        TCountryCodeAndName tCountryCodeAndName = null;

        // when
        Country country = CountryMapper.MAPPER.convert(tCountryCodeAndName);

        // then
        Assertions.assertThat(country).isNull();
    }

    private <T> T unmarshal(Class<T> clazz) {
        return JAXB.unmarshal(Objects.requireNonNull(CountryMapperTest.class
                .getResourceAsStream("/xml/country.xml")), clazz);
    }
}
