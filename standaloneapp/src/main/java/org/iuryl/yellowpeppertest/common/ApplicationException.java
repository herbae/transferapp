package org.iuryl.yellowpeppertest.common;

public abstract class ApplicationException extends RuntimeException {
    
    private long errorCode;

    public ApplicationException(long errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public long getErrorCode() {
        return errorCode;
    }
}
