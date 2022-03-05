package org.iuryl.mstest.exchange.exception;

import org.iuryl.mstest.common.ApplicationException;

public class ExchangeBrokerNotAvailable extends ApplicationException {

    private static final long ERROR_CODE = 215;

    public ExchangeBrokerNotAvailable() {
        super(ERROR_CODE, "Exchange broker returned anormal response.");
    }
    
}
