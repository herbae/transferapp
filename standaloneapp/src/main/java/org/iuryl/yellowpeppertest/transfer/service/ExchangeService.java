package org.iuryl.yellowpeppertest.transfer.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.iuryl.yellowpeppertest.transfer.model.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeService.class);

    @Value("${org.iuryl.mstest.fixer.io.key:0}")
    private String fixerIoKey;

    private RestTemplate restTemplate;

    private String URL = "http://data.fixer.io/api/latest?access_key=%s&symbols=%s";
    
    public ExchangeService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public BigDecimal convert(BigDecimal amount, Currency from, Currency to) {
        String urlWithSymbols = String.format(URL, fixerIoKey, from.toString() + "," + to.toString());

        log.info("URL {}", urlWithSymbols);

        Map<String, BigDecimal> rates = this.restTemplate.getForObject(urlWithSymbols, FixerOutput.class).rates;

        if (rates == null) {
            //something went wrong with exchange service
            return BigDecimal.ZERO; // fallback
        }

        BigDecimal fromRate = rates.get(from.toString());
        BigDecimal toRate = rates.get(to.toString());

        return amount.divide(fromRate, 8, RoundingMode.HALF_EVEN)
            .multiply(toRate)
            .setScale(2, RoundingMode.HALF_EVEN);
    }
}
