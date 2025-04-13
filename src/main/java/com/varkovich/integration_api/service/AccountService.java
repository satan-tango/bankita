package com.varkovich.integration_api.service;

import com.varkovich.integration_api.model.Account;
import com.varkovich.integration_api.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void accountVerification(String accountNumber) {
        if (accountRepository.findByAccountNumber(accountNumber).isEmpty()) {
            accountRepository.save(Account.builder().accountNumber(accountNumber).build());
        }
    }
}
