package org.iuryl.mstest.exchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.iuryl.mstest.exchange.dto.ExchangeDto;
import org.iuryl.mstest.exchange.dto.ExchangeOutDto;
import org.iuryl.mstest.exchange.exception.ExchangeBrokerNotAvailable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExchangeService {
    
    @Value("${org.iuryl.mstest.fixer.io.key}")
    private String fixerIoKey;

    private RestTemplate restTemplate;

    private String URL = "http://data.fixer.io/api/latest?access_key=%s&symbols=%s";
    
    public ExchangeService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ExchangeOutDto convert(ExchangeDto dto) {
        String urlWithSymbols = String.format(URL, fixerIoKey, dto.getFromCurrency() + "," + dto.getToCurrency());

        log.info("URL {}", urlWithSymbols);

        log.info("Will ask for rates {} ", dto);
        Map<String, BigDecimal> rates = this.restTemplate.getForObject(urlWithSymbols, FixerOutput.class).rates;

        if (rates == null) {
            throw new ExchangeBrokerNotAvailable();
        }

        log.info("Rates returned {} ", rates);
        BigDecimal fromRate = rates.get(dto.getFromCurrency());
        BigDecimal toRate = rates.get(dto.getToCurrency());


        BigDecimal amountConverted = dto.getAmount().divide(fromRate, 8, RoundingMode.HALF_EVEN)
            .multiply(toRate)
            .setScale(2, RoundingMode.HALF_EVEN);

        return new ExchangeOutDto(amountConverted);
    }
}
