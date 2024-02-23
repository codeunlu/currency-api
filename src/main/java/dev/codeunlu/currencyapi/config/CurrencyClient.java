package dev.codeunlu.currencyapi.config;

import dev.codeunlu.currencyapi.dto.client.ClientResponse;
import dev.codeunlu.currencyapi.model.Currency;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Map;

public interface CurrencyClient {
    @GetExchange("/latest?base_currency={baseCode}&currencies={code}")
    ClientResponse getExchangeRates(@PathVariable String baseCode, @PathVariable String code);
}
