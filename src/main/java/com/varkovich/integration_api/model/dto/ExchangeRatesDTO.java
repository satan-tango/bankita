package com.varkovich.integration_api.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Map;

@Component
@Scope("prototype")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRatesDTO {

    private long timestamp;

    private Map<String, Double> rates;
}
