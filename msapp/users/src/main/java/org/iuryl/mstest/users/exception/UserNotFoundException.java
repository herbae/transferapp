package org.iuryl.mstest.users.exception;

import org.iuryl.mstest.common.ApplicationException;

public class UserNotFoundException extends ApplicationException {

    private static final long ERROR_CODE = 356;

    public UserNotFoundException(long userId) {
        super(ERROR_CODE, String.format("User not found: %d", userId));
    }
    
}
