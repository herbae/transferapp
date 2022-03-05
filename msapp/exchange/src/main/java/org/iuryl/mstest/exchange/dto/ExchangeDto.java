package org.iuryl.mstest.exchange.dto;

import java.math.BigDecimal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExchangeDto {
    
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
}
