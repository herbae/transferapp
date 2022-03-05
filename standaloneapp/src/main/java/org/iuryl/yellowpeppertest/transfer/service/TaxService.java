package org.iuryl.yellowpeppertest.transfer.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

@Service
public class TaxService {
    
    private static final BigDecimal SMALLER_TAX = new BigDecimal("0.02");
    private static final BigDecimal BIGGER_TAX = new BigDecimal("0.05");
    private static final BigDecimal THRESHOLD = new BigDecimal("100.00");

    public BigDecimal calculateTax(BigDecimal amount) {

        //assuming only values bigger than 100 will have a tax of 5%
        if (amount.compareTo(THRESHOLD) > 0) {
            return amount.multiply(BIGGER_TAX).setScale(2);
        } else {
            return amount.multiply(SMALLER_TAX).setScale(2);
        }
    }
}
