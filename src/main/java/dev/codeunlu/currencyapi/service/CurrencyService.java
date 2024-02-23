package dev.codeunlu.currencyapi.service;

import dev.codeunlu.currencyapi.config.CurrencyClient;
import dev.codeunlu.currencyapi.dto.request.RequestExchange;
import dev.codeunlu.currencyapi.dto.response.ResponseExchange;
import dev.codeunlu.currencyapi.dto.client.ClientResponse;
import dev.codeunlu.currencyapi.dto.client.CurrencyDTO;
import dev.codeunlu.currencyapi.model.Currency;
import dev.codeunlu.currencyapi.repository.CurrencyRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"currencies"})
public class CurrencyService {
    private final CurrencyRepository repository;
    private final CurrencyClient currencyClientService;

    @Cacheable(key = "#request.targetCurrency()")
    public ResponseExchange getConvertExchangeRates(RequestExchange request){

        Optional<Currency> currencyOptional = repository
                .findFirstByBaseCodeAndCodeOrderByUpdatedTime(request.mainCurrency(), request.targetCurrency());

        return currencyOptional.map( currency -> {
            if(currency.getResponsedTime().isBefore(LocalDateTime.now().minusHours(1))){
                return ResponseExchange.convert(getExchangeRatesFromAPI(request), request.cash());
            }
            return ResponseExchange.convert(currency, request.cash());
        }).orElseGet(() -> ResponseExchange.convert(getExchangeRatesFromAPI(request), request.cash()));
    }

    @CacheEvict(allEntries = true)
    @PostConstruct
    @Scheduled(fixedRateString = "${currency-management.cache-ttl}")
    public void clearCache(){}

    protected Currency getExchangeRatesFromAPI(RequestExchange request) {
        ClientResponse response = currencyClientService.getExchangeRates(request.mainCurrency(),
                request.targetCurrency());
        CurrencyDTO currencyDTO = response.data().get(request.targetCurrency());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        Currency currency = Currency
                .builder()
                .baseCode(request.mainCurrency())
                .code(currencyDTO.code())
                .value(currencyDTO.value())
                .updatedTime(LocalDateTime.parse(response.meta().lastUpdatedAt(), formatter))
                .responsedTime(LocalDateTime.now())
                .build();
        return repository.save(currency);
    }
}
