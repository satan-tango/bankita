package com.varkovich.integration_api.service;

import com.varkovich.integration_api.exception.ExchangeRateApiException;
import com.varkovich.integration_api.model.ExceededTransactionLimit;
import com.varkovich.integration_api.model.ExchangeRate;
import com.varkovich.integration_api.model.Limit;
import com.varkovich.integration_api.model.Transaction;
import com.varkovich.integration_api.model.dto.LimitDTO;
import com.varkovich.integration_api.model.dto.TransactionResponseDTO;
import com.varkovich.integration_api.repository.ExceededTransactionLimitRepository;
import com.varkovich.integration_api.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {

    private final ExchangeRatesService exchangeRatesService;

    private final LimitService limitService;

    private final TransactionRepository transactionRepository;

    private final AccountService accountService;

    private final ExceededTransactionLimitRepository exceededTransactionLimitRepository;

    // @Transactional(rollbackOn = Exception.class)
    public Optional<TransactionResponseDTO> executeTransaction(Transaction transaction) throws ExchangeRateApiException, AccountNotFoundException {
        accountService.accountVerification(transaction.getAccountFrom());
        transactionRepository.save(transaction);
        ExchangeRate exchangeRate = exchangeRatesService.getExchangeRate(transaction.getCurrencyShortName());
        BigDecimal sumInUsd = transaction.getSum().divide(exchangeRate.getClose(), 2, BigDecimal.ROUND_HALF_UP);
        Limit accountLimit = limitService.getUpdatedRemainigAccountLimit(transaction.getAccountFrom(), sumInUsd, transaction.getCategory());
        ExceededTransactionLimit exceededTransactionLimit = ExceededTransactionLimit.builder()
                .transactionId(transaction.getId())
                .limitId(accountLimit.getId())
                .limitExceeded(accountLimit.getLimitRemaining().compareTo(BigDecimal.ZERO) >= 0 ? false : true)
                .build();
        exceededTransactionLimitRepository.save(exceededTransactionLimit);
        System.out.println(sumInUsd);
        return null;
    }


}
