package dev.codeunlu.currencyapi.config;

import dev.codeunlu.currencyapi.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Value("${currency-management.api-url}")
    private String API_URL;

    @Value("${currency-management.api-key}")
    private String API_KEY;

    @Bean
    public CurrencyClient currencyClient(){
        RestClient client = RestClient
                .builder()
                .baseUrl(API_URL)
                .defaultHeader("apikey", API_KEY)
                .defaultStatusHandler(status -> status.value() == 404, (request, response) -> {
                    throw new NotFoundException(response.getStatusText());
                })
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(client))
                .build();
        return factory.createClient(CurrencyClient.class);
    }
}
