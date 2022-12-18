package ru.info.country.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.info.country.dto.RequestDto;
import ru.info.country.dto.ResponseDto;
import ru.info.country.entity.CachedRequest;
import ru.info.country.exception.CachedRequestExpiredDateException;
import ru.info.country.exception.CustomException;
import ru.info.country.repository.CachedRequestRepository;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CachedRequestService {

    private final CachedRequestRepository cachedRequestRepository;

    private final ObjectMapper objectMapper;

    public ResponseDto getFromCache(RequestDto request) {

        String requestBody = request.getRequest().toString();
        String requestBodyHash = Hashing.sha256()
                .hashString(requestBody, StandardCharsets.UTF_8)
                .toString();

        CachedRequest cachedRequest = cachedRequestRepository
                .findTopByServiceAndVersionAndRequestBodyHash(
                        request.getService(), request.getVersion(), requestBodyHash)
                .orElse(null);

        if (cachedRequest == null) {
            return null;
        }

        if (cachedRequest.getExpiredDate().isBefore(LocalDateTime.now())) {
            throw new CachedRequestExpiredDateException();
        }

        ResponseDto responseDto = new ResponseDto();
        responseDto.setService(request.getService());
        responseDto.setVersion(request.getVersion());
        try {
            responseDto.setResponse(objectMapper.readTree(cachedRequest.getResponseBody()));
        } catch (JsonProcessingException e) {
            throw new CustomException(request.getService(), request.getVersion(), e);
        }
        return responseDto;
    }

    @Transactional
    public void putCached(RequestDto request, ResponseDto response) {

        String requestBody = request.getRequest().toString();
        String requestBodyHash = Hashing.sha256()
                .hashString(request.getRequest().toString(), StandardCharsets.UTF_8)
                .toString();
        String responseBody = response.getResponse().toString();

        CachedRequest cachedRequest = cachedRequestRepository
                .findTopByServiceAndVersionAndRequestBodyHash(request.getService(),
                        request.getVersion(), requestBodyHash).orElse(new CachedRequest());
        cachedRequest.setService(request.getService());
        cachedRequest.setVersion(request.getVersion());
        cachedRequest.setRequestBody(requestBody);
        cachedRequest.setRequestBodyHash(requestBodyHash);
        cachedRequest.setResponseBody(responseBody);

        LocalDateTime timeNow = LocalDateTime.now();
        if (cachedRequest.getCreateDate() == null) {
            cachedRequest.setCreateDate(timeNow);
        }
        cachedRequest.setModifiedDate(timeNow);
        cachedRequest.setExpiredDate(timeNow.plusMinutes(1));

        cachedRequestRepository.save(cachedRequest);
    }
}
