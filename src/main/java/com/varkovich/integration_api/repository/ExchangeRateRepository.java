package com.varkovich.integration_api.repository;

import com.varkovich.integration_api.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByCurrencyPair(String currencyPair);
}
