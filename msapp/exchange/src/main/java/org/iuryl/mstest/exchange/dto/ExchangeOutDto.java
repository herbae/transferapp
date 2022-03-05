package org.iuryl.mstest.exchange.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExchangeOutDto {
    
    private BigDecimal amount;
}
