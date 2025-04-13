package com.varkovich.integration_api.exception;

public class ExchangeRateApiException extends Exception {


    public ExchangeRateApiException() {
        super("Received a bad response from the exchange rate API.");
    }

    public ExchangeRateApiException(String message) {
        super(message);
    }

}
