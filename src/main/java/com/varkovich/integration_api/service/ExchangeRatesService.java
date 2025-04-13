package com.varkovich.integration_api.service;

import com.varkovich.integration_api.client.CurrencyApiClient;
import com.varkovich.integration_api.exception.ExchangeRateApiException;
import com.varkovich.integration_api.model.ExchangeRate;
import com.varkovich.integration_api.model.dto.ExchangeRateDTO;
import com.varkovich.integration_api.repository.ExchangeRateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ExchangeRatesService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final CurrencyApiClient currencyApiClient;

    public ExchangeRate getExchangeRate(String currencyShortName) throws ExchangeRateApiException {
        Optional<ExchangeRate> exchangeRateFromDB = exchangeRateRepository.findByCurrencyPair(currencyShortName + "-USD");
        if (exchangeRateFromDB.isEmpty()) {
            ExchangeRate freshExchangeRate = null;
            try {
                freshExchangeRate = getFreshExchangeRate(currencyShortName);
            } catch (ExchangeRateApiException e) {
                //Received a bad response from API(day off or holiday), and we do not have a record id db, so we throw exception
                log.error("Received a bad response from the exchange rate API: ", e.getMessage() +
                        ". Do not have a record in db, throw exception.");
                throw new ExchangeRateApiException();
            }
            exchangeRateRepository.save(freshExchangeRate);
            log.info("Received exchange rate from the exchange rate API->{}:{} {}. Saved exchange rate to the db ", freshExchangeRate.getCurrencyPair(), freshExchangeRate.getClose(), freshExchangeRate.getTimestamp());
            return freshExchangeRate;
        } else {
            if (isRateStale(exchangeRateFromDB.get())) {
                ExchangeRate exchangeRate = null;
                try {
                    exchangeRate = getFreshExchangeRate(currencyShortName);
                    exchangeRateRepository.delete(exchangeRateFromDB.get());
                    exchangeRateRepository.save(exchangeRate);
                    log.info("The exchange rate {}:{} {} has been stale. ", exchangeRateFromDB.get().getCurrencyPair(), exchangeRateFromDB.get().getClose(), exchangeRateFromDB.get().getTimestamp());
                    log.info("Stale exchange rate {}:{} {} has been deleted from db.", exchangeRateFromDB.get().getCurrencyPair(), exchangeRateFromDB.get().getClose(), exchangeRateFromDB.get().getTimestamp());
                    log.info("Fresh exchange rate {}:{} {} has been saved to db.", exchangeRate.getCurrencyPair(), exchangeRate.getClose(), exchangeRate.getTimestamp());
                    return exchangeRate;
                } catch (ExchangeRateApiException e) {
                    //Received a bad response from API(day off or holiday), and we do  have a record id db, so we return previous close
                    log.warn("The exchange rate {}:{} {} has been stale. ", exchangeRateFromDB.get().getCurrencyPair(), exchangeRateFromDB.get().getClose(), exchangeRateFromDB.get().getTimestamp());
                    log.warn("Received a bad response from API(day off or holiday), and we do  have a record id db, so we return previous close exchange rate from db {}:{} {}", exchangeRateFromDB.get().getCurrencyPair(), exchangeRateFromDB.get().getClose(), exchangeRateFromDB.get().getTimestamp());
                    return exchangeRateFromDB.get();
                }

            } else {
                log.info("The exchange rate {}:{} {}  is not out of date", exchangeRateFromDB.get().getCurrencyPair(), exchangeRateFromDB.get().getClose(), exchangeRateFromDB.get().getTimestamp());
                return exchangeRateFromDB.get();
            }

        }
    }

    private boolean isRateStale(ExchangeRate exchangeRate) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long differenceInMillis = now.getTime() - exchangeRate.getTimestamp().getTime();
        //if it is out of date by 24 hours
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        return differenceInMillis > oneDayInMillis;
    }

    private ExchangeRate getFreshExchangeRate(String currencyShortName) throws ExchangeRateApiException {
        Optional<ExchangeRateDTO> exchangeRateDTO = currencyApiClient.getExchangeRates(currencyShortName);
        if (exchangeRateDTO.isEmpty()) {
            throw new ExchangeRateApiException();
        }
        return ExchangeRate.builder()
                .currencyPair(currencyShortName + "-USD")
                .close(BigDecimal.valueOf(exchangeRateDTO.get().getRates().get(currencyShortName)))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
