package org.iuryl.mstest.transfers.exception;

import org.iuryl.mstest.common.ApplicationException;

public class MaxLimitTransferPerDayException extends ApplicationException {
    
    private static final long ERROR_CODE = 459;

    public MaxLimitTransferPerDayException(long userId) {
        super(ERROR_CODE, String.format("User %d reached max limit of transfers per day.",
            userId));
    }
}
