package com.varkovich.integration_api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.varkovich.integration_api.config.ExchangeRatesConfig;
import com.varkovich.integration_api.exception.ExchangeRateApiException;
import com.varkovich.integration_api.model.dto.ExchangeRateDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyApiClient {

    private final ExchangeRatesConfig exchangeRatesConfig;

    private ExchangeRateDTO exchangeRateDTO;

    public Optional<ExchangeRateDTO> getExchangeRates(String currencyShortName) throws ExchangeRateApiException {
        OkHttpClient client = new OkHttpClient();

        StringBuilder url = new StringBuilder();
        url.append(exchangeRatesConfig.getUri());
        url.append("?app_id=");
        url.append(exchangeRatesConfig.getApiKey());
        url.append("&base=USD");
        url.append("&symbols=" + currencyShortName);
        url.append("&prettyprint=false&show_alternative=false");


        Request request = new Request.Builder()
                .url(url.toString())
                .get()
                .addHeader("accept",
                        "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ObjectMapper objectMapper = new ObjectMapper();
                exchangeRateDTO = objectMapper.readValue(response.body().string(), ExchangeRateDTO.class);
                return Optional.of(exchangeRateDTO);
            } else {
                throw new ExchangeRateApiException("Received a bad response from the exchange rate API.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
