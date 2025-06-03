package com.jakem.nettracker.plaid;

import com.plaid.client.request.PlaidApi;
import com.plaid.client.ApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(PlaidProperties.class)
@RequiredArgsConstructor
public class PlaidConfig {

    private final PlaidProperties plaidProperties;

    @Bean
    public PlaidApi plaidApi() {
        Map<String,String> apiKeys = Map.of(
                "clientId", plaidProperties.clientId(),
                "secret", plaidProperties.secret(),
                "plaidVersion", "2020-09-14"
        );

        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(plaidProperties.url());

        return apiClient.createService(PlaidApi.class);
    }
}
