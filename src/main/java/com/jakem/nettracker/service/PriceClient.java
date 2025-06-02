package com.jakem.nettracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Service
public class PriceClient {

    private final WebClient web;
    private final String apiKey;

    public PriceClient(@Value("${price.api.base}") String baseUrl,
                       @Value("${price.api.key}") String apiKey) {
        this.web = WebClient.builder().baseUrl(baseUrl).build();
        this.apiKey = apiKey;
    }

    public BigDecimal latestPrice(String symbol) {
        JsonNode json = web.get()
                .uri(uri -> uri.queryParam("symbol", symbol)
                        .queryParam("token", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        return new BigDecimal(json.get("c").asText());
    }
}
