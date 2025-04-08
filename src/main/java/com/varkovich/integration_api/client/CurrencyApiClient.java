package com.varkovich.integration_api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.varkovich.integration_api.config.ExchangeRatesConfig;
import com.varkovich.integration_api.exception.ExchangeRateApiException;
import com.varkovich.integration_api.model.dto.ExchangeRatesDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CurrencyApiClient {

    private final ExchangeRatesConfig exchangeRatesConfig;

    private ExchangeRatesDTO exchangeRatesDTO;

    public Optional<ExchangeRatesDTO> getExchangeRates() throws ExchangeRateApiException {
        OkHttpClient client = new OkHttpClient();

        StringBuilder url = new StringBuilder();
        url.append(exchangeRatesConfig.getUri());
        url.append("?app_id=");
        url.append(exchangeRatesConfig.getApiKey());
        url.append("&base=USD&symbols=RUB%2CBYN%2CKZT&prettyprint=false&show_alternative=false");


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
                exchangeRatesDTO = objectMapper.readValue(response.body().string(), ExchangeRatesDTO.class);
                return Optional.of(exchangeRatesDTO);
            } else {
                throw new ExchangeRateApiException("Received a bad response from the exchange rate API.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
