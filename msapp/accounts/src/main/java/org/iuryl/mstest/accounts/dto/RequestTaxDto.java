package org.iuryl.mstest.accounts.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestTaxDto {
    
    private BigDecimal amount;
}
