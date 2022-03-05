package org.iuryl.yellowpeppertest.transfer.exception;

import org.iuryl.yellowpeppertest.common.ApplicationException;

public class MaxLimitTransferPerDayException extends ApplicationException {
    
    private static final long ERROR_CODE = 459;

    public MaxLimitTransferPerDayException(String userName) {
        super(ERROR_CODE, String.format("User %s reached max limit of transfers per day.",
            userName));
    }
}
