package org.iuryl.yellowpeppertest.transfer.exception;

import org.iuryl.yellowpeppertest.common.ApplicationException;

public class CurrencyNotAcceptedException extends ApplicationException {
    
    private static final long ERROR_CODE = 457;

    public CurrencyNotAcceptedException(String currencyId) {
        super(ERROR_CODE, String.format("Currency not accepted %s.", currencyId));
    }
}
