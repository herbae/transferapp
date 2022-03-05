package org.iuryl.mstest.common;

public class NotAuthorizedException extends ApplicationException {
    
    private static final long ERROR_CODE = 425;

    public NotAuthorizedException(long userId, String info) {
        super(ERROR_CODE, String.format("User %d can't access %s.",
            userId, info));
    }
}
