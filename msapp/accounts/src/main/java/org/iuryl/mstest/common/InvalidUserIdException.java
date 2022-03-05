package org.iuryl.mstest.common;

public class InvalidUserIdException extends ApplicationException {
    
    private static final long ERROR_CODE = 483;

    public InvalidUserIdException(String userId) {
        super(ERROR_CODE, String.format("User ID %s could not be found in request.", userId));
    }
}
