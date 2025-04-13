package com.varkovich.integration_api.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String accountNumber) {
        super("Account " + accountNumber + " has not been found.");
    }
}
