package com.varkovich.integration_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity validationException(ValidationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeRateApiException.class)
    public ResponseEntity exchangeRateApiException(ExchangeRateApiException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity accountNotFoundException(ExchangeRateApiException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity exception(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
