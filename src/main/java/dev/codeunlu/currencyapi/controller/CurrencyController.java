package dev.codeunlu.currencyapi.controller;

import dev.codeunlu.currencyapi.dto.common.BaseResponse;
import dev.codeunlu.currencyapi.dto.request.RequestExchange;
import dev.codeunlu.currencyapi.dto.response.ResponseExchange;
import dev.codeunlu.currencyapi.service.CurrencyService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/currencies")
@RequiredArgsConstructor
@Validated
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("convert")
    @RateLimiter(name = "api-get")
    public ResponseEntity<BaseResponse<ResponseExchange>> getConvertExchangeRates(@RequestBody RequestExchange requestExchange){
        return ResponseEntity.ok(new BaseResponse<>(currencyService.getConvertExchangeRates(requestExchange)));
    }
}
