package org.iuryl.mstest.tax.repository;

import java.math.BigDecimal;

import org.iuryl.mstest.tax.dto.TaxDto;
import org.springframework.stereotype.Repository;

@Repository
public class TaxRepository {

    private static final BigDecimal SMALLER_TAX = new BigDecimal("0.02");
    private static final BigDecimal BIGGER_TAX = new BigDecimal("0.05");
    private static final BigDecimal THRESHOLD = new BigDecimal("100.00");

    public TaxDto applyTax(BigDecimal amount) {
        //assuming only values bigger than 100 will have a tax of 5%
        BigDecimal tax = amount.compareTo(THRESHOLD) > 0 ? BIGGER_TAX : SMALLER_TAX;

        return new TaxDto(amount, tax, amount.multiply(tax).setScale(2));
    }
}
