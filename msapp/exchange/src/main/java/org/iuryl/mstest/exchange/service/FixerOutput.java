package org.iuryl.mstest.exchange.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FixerOutput {
    public String base;
    public LocalDate date;
    public Map<String, BigDecimal> rates;

    @Override
    public String toString() {
        return "FixerOutput [base=" + base + ", date=" + date + ", rates=" + rates + "]";
    }
}
