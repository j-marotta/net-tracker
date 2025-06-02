package com.jakem.nettracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.jakem.nettracker.pricing.PriceClient;    // ← interface
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FinnhubPriceClient implements PriceClient {

    private final WebClient web;
    private final String apiKey;

    public FinnhubPriceClient(@Value("${price.api.base}") String baseUrl,
                              @Value("${price.api.key}")  String apiKey) {
        this.web    = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    @Override
    public Optional<BigDecimal> quote(String symbol) {

        JsonNode json = web.get()
                .uri(uri -> uri.queryParam("symbol", symbol)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        resp -> Mono.error(new RuntimeException("Bad ticker")))
                .bodyToMono(JsonNode.class)
                .onErrorResume(e -> Mono.empty())
                .block();

        if (json == null || json.get("c") == null || json.get("c").asDouble() == 0)
            return Optional.empty();

        return Optional.of(new BigDecimal(json.get("c").asText()));
    }


    public BigDecimal latestPrice(String symbol) {
        return quote(symbol).orElseThrow(
                () -> new IllegalArgumentException("Ticker not found: " + symbol));
    }
}
