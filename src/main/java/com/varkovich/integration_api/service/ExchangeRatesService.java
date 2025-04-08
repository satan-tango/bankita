package com.varkovich.integration_api.service;

import com.varkovich.integration_api.dao.ExchangeRatesDAO;
import com.varkovich.integration_api.model.ExchangeRates;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExchangeRatesService {
    private final ExchangeRatesDAO exchangeRatesDAO;

    public Optional<ExchangeRates> findLatestExchangeRateByCurrencyPair(String currencyPair) {
        return exchangeRatesDAO.findLatestExchangeRateByCurrencyPair(currencyPair);
    }
}
