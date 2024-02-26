package dev.codeunlu.currencyapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.codeunlu.currencyapi.dto.request.RequestExchange;
import dev.codeunlu.currencyapi.dto.response.ResponseExchange;
import dev.codeunlu.currencyapi.exception.GlobalExceptionHandler;
import dev.codeunlu.currencyapi.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.math.BigDecimal;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "server.port=0")
@DirtiesContext
@ActiveProfiles(value = "dev")
class CurrencyControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(new CurrencyController(currencyService))
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    void itShouldConvertCurrency_WhenValidCurrencyRequestBody() throws Exception {
        RequestExchange request =
                new RequestExchange("EUR", "TRY", BigDecimal.valueOf(13));

        ResponseExchange responseDto =
                new ResponseExchange("EUR", "TRY", BigDecimal.valueOf(429));

        // when
        when(currencyService.getConvertExchangeRates(request)).thenReturn(responseDto);

        // Api Call
        mockMvc.perform(get("/api/v1/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.totalCash", is("429")));
    }
}