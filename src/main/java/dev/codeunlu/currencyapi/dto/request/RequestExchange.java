package dev.codeunlu.currencyapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RequestExchange(@NotNull(message = "Invalid Currency: Main currency is NULL")
                              String mainCurrency,
                              @NotNull(message = "Invalid Currency: Target currency is NULL")
                              String targetCurrency,
                              @DecimalMin(value = "1.0", inclusive = false)
                              BigDecimal cash) {}
