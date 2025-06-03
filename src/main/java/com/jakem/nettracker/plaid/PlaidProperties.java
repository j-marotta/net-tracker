package com.jakem.nettracker.plaid;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "plaid")
public record PlaidProperties(@NotBlank String clientId, @NotBlank String secret, @DefaultValue String url) {
}
