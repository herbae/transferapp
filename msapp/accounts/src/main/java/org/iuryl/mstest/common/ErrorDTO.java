package org.iuryl.mstest.common;

public class ErrorDTO {
    public long errorCode;
    public String message;

    public ErrorDTO(long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
