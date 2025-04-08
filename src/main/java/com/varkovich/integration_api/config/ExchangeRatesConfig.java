package com.varkovich.integration_api.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ExchangeRatesConfig {

    @Value("${service.exchange-rate_uri}")
    private String uri;

    @Value("${service.exchange-rate_api}")
    private String apiKey;
}
