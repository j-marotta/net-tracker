package com.jakem.nettracker.pricing;

import java.math.BigDecimal;
import java.util.Optional;

public interface PriceClient {

    Optional<BigDecimal> quote(String symbol, String category);

    default Optional<BigDecimal> quote(String symbol) {
        return quote(symbol, "STOCK");
    }
}
