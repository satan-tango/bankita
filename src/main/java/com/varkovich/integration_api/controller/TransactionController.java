package com.varkovich.integration_api.controller;

import com.varkovich.integration_api.exception.ExchangeRateApiException;
import com.varkovich.integration_api.exception.ValidationException;
import com.varkovich.integration_api.model.Transaction;
import com.varkovich.integration_api.model.dto.TransactionResponseDTO;
import com.varkovich.integration_api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<TransactionResponseDTO> transaction(@RequestBody @Valid Transaction transaction,
                                                              BindingResult bindingResult) throws ValidationException, ExchangeRateApiException {
        if (bindingResult.hasErrors()) {
            List<String> messages = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                messages.add(allError.getDefaultMessage());
            }
            throw new ValidationException(messages.stream().collect(Collectors.joining("\n")));
        }

        Optional<TransactionResponseDTO> transactionResponseDTO = transactionService.executeTransaction(transaction);

        if (transactionResponseDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(transactionResponseDTO.get(), HttpStatus.OK);
    }
}
