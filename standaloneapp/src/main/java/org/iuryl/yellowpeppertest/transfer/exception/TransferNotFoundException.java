package org.iuryl.yellowpeppertest.transfer.exception;

import java.util.UUID;

import org.iuryl.yellowpeppertest.common.ApplicationException;

public class TransferNotFoundException extends ApplicationException {
    
    private static final long ERROR_CODE = 458;

    public TransferNotFoundException(UUID transferId) {
        super(ERROR_CODE, String.format("Transfer not found %s.", transferId));
    }
}
