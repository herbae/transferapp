package org.iuryl.mstest.tax.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaxDto {
    
    private BigDecimal amount;
    private BigDecimal taxPercent;
    private BigDecimal calculatedTax;
}
