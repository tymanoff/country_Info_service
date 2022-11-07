package ru.info.country.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.oorsprong.websamples.TLanguage;
import ru.info.country.entity.Language;

@Mapper
public interface LanguageMapper {
    LanguageMapper MAPPER = Mappers.getMapper(LanguageMapper.class);

    @Mapping(source = "SISOCode", target = "isoCode")
    @Mapping(source = "SName", target = "name")
    Language convert(TLanguage tLanguage);
}
