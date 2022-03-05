package org.iuryl.mstest.transfers.exception;

import org.iuryl.mstest.common.ApplicationException;

public class CurrencyNotAcceptedException extends ApplicationException {

    private static final long ERROR_CODE = 402;

    public CurrencyNotAcceptedException(String currencyId) {
        super(ERROR_CODE, String.format("Currency not accepted: %s", currencyId));
    }
    
}
