package com.jakem.nettracker;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssetRequest {

    @NotBlank(message = "Asset name is required")
    String name;

    @NotBlank(message = "Asset category is required")
    String category;

    @Positive(message = "Units must be positive")
    BigDecimal units;

    @PositiveOrZero(message = "Unit value must be at least zero")
    BigDecimal unitValue;

    @PositiveOrZero(message = "Purchase price must be at least zero")
    BigDecimal purchasePrice;
}
