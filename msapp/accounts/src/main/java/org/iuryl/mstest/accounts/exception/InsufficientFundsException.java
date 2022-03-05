package org.iuryl.mstest.accounts.exception;

import org.iuryl.mstest.common.ApplicationException;

public class InsufficientFundsException extends ApplicationException {

    private static final long ERROR_CODE = 457;

    public InsufficientFundsException(String accountNumber) {
        super(ERROR_CODE, String.format("Insufficient funds for account: %s", accountNumber));
    }
    
}
