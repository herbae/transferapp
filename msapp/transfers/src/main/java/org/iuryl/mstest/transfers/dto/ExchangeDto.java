package org.iuryl.mstest.transfers.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeDto {
    
    private String fromCurrency;
    private String toCurrency;
    private BigDecimal amount;
}
