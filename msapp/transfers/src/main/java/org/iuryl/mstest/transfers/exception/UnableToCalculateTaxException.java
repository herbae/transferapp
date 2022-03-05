package org.iuryl.mstest.transfers.exception;

import org.iuryl.mstest.common.ApplicationException;

public class UnableToCalculateTaxException extends ApplicationException {

    private static final long ERROR_CODE = 185;

    public UnableToCalculateTaxException(Throwable cause) {
        super(ERROR_CODE, String.format("Tax service unavailable or error in call."), cause);
    }

}
