package com.varkovich.integration_api.dao;

import com.varkovich.integration_api.model.ExchangeRates;
import com.varkovich.integration_api.repository.ExchangeRatesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ExchangeRatesDAO {

    private final ExchangeRatesRepository exchangeRatesRepository;

    public Optional<ExchangeRates> findLatestExchangeRateByCurrencyPair(String currencyPair) {
        return exchangeRatesRepository.findLatestExchangeRateByCurrencyPair(currencyPair);
    }
}
