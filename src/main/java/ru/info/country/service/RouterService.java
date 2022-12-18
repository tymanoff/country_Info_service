package ru.info.country.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import ru.info.country.dto.RequestDto;
import ru.info.country.dto.ResponseDto;
import ru.info.country.exception.CachedRequestNotFoundException;
import ru.info.country.exception.CountryRequestServiceNotFoundException;

@Service
@RequiredArgsConstructor
public class RouterService {

    private final CachedRequestService cachedRequestService;
    private final CountryRequestService countryRequestService;
    private final CounterService counterService;

    public ResponseDto request(RequestDto request) {
        ResponseDto response;

        if (BooleanUtils.isTrue(request.getCached())) {
            response = cachedRequestService.getFromCache(request);
            if (response == null) {
                throw new CachedRequestNotFoundException();
            }
        } else {
            response = countryRequestService.getCountryInfo(request);
            if (response == null) {
                throw new CountryRequestServiceNotFoundException();
            }

            if (BooleanUtils.isNotTrue(request.getCached())) {
                cachedRequestService.putCached(request, response);
            }
        }
        counterService.increment(request.getService(), request.getVersion(), request.getCached());
        return response;
    }
}
