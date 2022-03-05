package org.iuryl.mstest.transfers.exception;

import org.iuryl.mstest.common.ApplicationException;

public class AccountNumberNotFoundException extends ApplicationException {

    private static final long ERROR_CODE = 456;

    public AccountNumberNotFoundException(String accountNumber) {
        super(ERROR_CODE, String.format("Account not found: %s", accountNumber));
    }
    
}
