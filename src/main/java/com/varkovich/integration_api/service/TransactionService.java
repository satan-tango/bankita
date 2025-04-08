package com.varkovich.integration_api.service;

import com.varkovich.integration_api.client.CurrencyApiClient;
import com.varkovich.integration_api.dao.ExchangeRatesDAO;
import com.varkovich.integration_api.exception.ExchangeRateApiException;
import com.varkovich.integration_api.model.ExchangeRates;
import com.varkovich.integration_api.model.Transaction;
import com.varkovich.integration_api.model.dto.TransactionResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {

    private final CurrencyApiClient currencyApiClient;

    private final ExchangeRatesService exchangeRatesService;

    public Optional<TransactionResponseDTO> executeTransaction(Transaction transaction) throws ExchangeRateApiException {
        //  Optional<ExchangeRatesDTO> exchangeRates = currencyApiClient.getExchangeRates();

        Optional<ExchangeRates> exchangeRate = exchangeRatesService.findLatestExchangeRateByCurrencyPair("USD-BYN");
        if (exchangeRate.isEmpty()) {
            //TODO получить данные через API, обновить базу, предоставить курс валют
        }

        //TODO сравниваю входит ли в сутки, если да, использую этот курс, если нет, делаю запрос, обновляю базу
        System.out.println("dfsfsd");
        return null;
    }
}
