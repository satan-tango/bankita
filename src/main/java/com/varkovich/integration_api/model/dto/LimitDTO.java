package com.varkovich.integration_api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;


@Builder
@AllArgsConstructor
public class LimitDTO {

    private BigDecimal amount;

    private BigDecimal limitRemaining;

    private String category;

    private String limitCurrency;
}
