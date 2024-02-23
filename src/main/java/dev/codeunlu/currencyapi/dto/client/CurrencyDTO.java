package dev.codeunlu.currencyapi.dto.client;

import dev.codeunlu.currencyapi.model.Currency;

import java.math.BigDecimal;

public record CurrencyDTO(String code, BigDecimal value) {}
