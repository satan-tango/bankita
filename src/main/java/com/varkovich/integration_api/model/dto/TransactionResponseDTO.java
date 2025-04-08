package com.varkovich.integration_api.model.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
public class TransactionResponseDTO {

    private String account_from;

    private String account_to;

    private String category;

    private BigDecimal sum;

    private String currencyShortName;

    private Timestamp datetime;

    private BigDecimal limitSum;

    private Timestamp limitDatetime;

    private String limitCurrencyShortName;
}
