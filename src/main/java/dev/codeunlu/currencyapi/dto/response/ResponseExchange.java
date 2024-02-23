package dev.codeunlu.currencyapi.dto.response;

import dev.codeunlu.currencyapi.model.Currency;

import java.math.BigDecimal;

public record ResponseExchange(
        String mainCurrency,
        String targetCurrency,
        BigDecimal totalCash) {

    public static ResponseExchange convert(Currency currency, BigDecimal cash){
        return new ResponseExchange(
                currency.getBaseCode(),
                currency.getCode(),
                cash.multiply(currency.getValue())
        );
    }
}
