package org.iuryl.yellowpeppertest.account.exception;

import org.iuryl.yellowpeppertest.common.ApplicationException;

public class AccountNotFoundException extends ApplicationException {
    
    private static final long ERROR_CODE = 356;

    public AccountNotFoundException(long id) {
        super(ERROR_CODE, String.format("Could not find account # %d", id));
    }
}
