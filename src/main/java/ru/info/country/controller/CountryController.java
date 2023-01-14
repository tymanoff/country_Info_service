package ru.info.country.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import ru.info.country.dto.RequestDto;
import ru.info.country.dto.ResponseDto;
import ru.info.country.exception.CustomException;
import ru.info.country.service.RouterService;

@RestController
@RequestMapping(path = "service")
@RequiredArgsConstructor
public class CountryController {

    private final RouterService routerService;

    @PostMapping
    @ResponseBody
    public ResponseDto request(@RequestBody RequestDto request) {
        try {
            return routerService.request(request);
        } catch (Exception e) {
            throw new CustomException(request.getService(), request.getVersion(), e);
        }
    }
}
