package dev.codeunlu.currencyapi.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Meta(
        @JsonProperty("last_updated_at")
        String lastUpdatedAt) {
}
