package dev.codeunlu.currencyapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Convert Service API")
                        .version("1.0")
                        .description("""
                                This is an api provides currency converting
                                for last 1 hours based on target currency
                                """
                        ));
    }
}
