package org.iuryl.mstest.accounts.controller;

import org.iuryl.mstest.common.ApplicationException;
import org.iuryl.mstest.common.ErrorDTO;
import org.iuryl.mstest.common.InvalidUserIdException;
import org.iuryl.mstest.common.NotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotAuthorizedAdvice {

    @ResponseBody
    @ExceptionHandler({ NotAuthorizedException.class, InvalidUserIdException.class })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDTO handler(ApplicationException ex) {
        return new ErrorDTO(ex.getErrorCode(), ex.getMessage());
    }
}