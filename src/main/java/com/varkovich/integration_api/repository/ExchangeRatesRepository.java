package com.varkovich.integration_api.repository;

import com.varkovich.integration_api.model.ExchangeRates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRates, Long> {
    @Query(value = "SELECT * FROM exchange_rates WHERE currency_pair = :currencyPair ORDER BY datetime DESC LIMIT 1", nativeQuery = true)
    Optional<ExchangeRates> findLatestExchangeRateByCurrencyPair(@Param("currencyPair") String currencyPair);
}
