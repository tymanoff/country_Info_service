package ru.info.country.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.info.country.dto.ErrorDto;
import ru.info.country.dto.ResponseDto;
import ru.info.country.exception.CustomException;

@ControllerAdvice
public class ExceptionConfiguration {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto> handleMissingRequestHeaderException(CustomException ex) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setService(ex.getService());
        responseDto.setVersion(ex.getVersion());

        ErrorDto errorDto = new ErrorDto();
        errorDto.setCode(Integer.toString(HttpStatus.BAD_REQUEST.value()));
        errorDto.setDescription(ex.getCause().getMessage());
        responseDto.setError(errorDto);

        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

}
