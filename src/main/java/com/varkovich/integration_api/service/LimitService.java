package com.varkovich.integration_api.service;

import com.varkovich.integration_api.model.Account;
import com.varkovich.integration_api.model.Limit;
import com.varkovich.integration_api.model.dto.LimitDTO;
import com.varkovich.integration_api.repository.AccountRepository;
import com.varkovich.integration_api.repository.LimitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LimitService {
    private final AccountRepository accountRepository;

    private final LimitRepository limitRepository;

    public Limit getUpdatedRemainigAccountLimit(String accountNumber, BigDecimal sum, String category) throws AccountNotFoundException {
        Limit limit = getAccountLimit(accountNumber, category);
        limit = updateAccountLimitRemaining(limit, sum);
        return limit;
    }

    public Limit getAccountLimit(String accountNumber, String category) throws AccountNotFoundException {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isEmpty()) {
            throw new AccountNotFoundException(accountNumber);
        }

        Optional<Limit> limitFromDB = limitRepository.findByAccountIdAndCategory(account.get().getId(), category);
        if (limitFromDB.isEmpty()) {
            Limit defaultLimit = Limit.builder()
                    .accountId(account.get().getId())
                    .limitRemaining(new BigDecimal(1000))
                    .limitCurrency("USD")
                    .category(category)
                    .amount(new BigDecimal(1000))
                    .build();
            limitRepository.save(defaultLimit);

            return defaultLimit;
        }
        return limitFromDB.get();
    }

    public Limit updateAccountLimitRemaining(Limit limit, BigDecimal sum) {
        limit.setLimitRemaining(limit.getLimitRemaining().subtract(sum));

        limitRepository.save(limit);

        return limit;
    }
}
