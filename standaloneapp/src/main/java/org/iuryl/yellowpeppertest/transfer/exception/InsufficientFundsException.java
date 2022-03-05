package org.iuryl.yellowpeppertest.transfer.exception;

import org.iuryl.yellowpeppertest.common.ApplicationException;
import org.iuryl.yellowpeppertest.transfer.model.Transfer;

public class InsufficientFundsException extends ApplicationException {
    
    private static final long ERROR_CODE = 456;

    public InsufficientFundsException(Transfer transfer) {
        super(ERROR_CODE, String.format("Account # %d does not have enough funds (transfer # %s).",
            transfer.getFromAccount().getId(),
            transfer.getId().toString()));
    }
}
