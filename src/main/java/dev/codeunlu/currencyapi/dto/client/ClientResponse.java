package dev.codeunlu.currencyapi.dto.client;

import java.util.Map;

public record ClientResponse(Meta meta, Map<String, CurrencyDTO> data) { }
