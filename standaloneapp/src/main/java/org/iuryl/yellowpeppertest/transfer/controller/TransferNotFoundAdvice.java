package org.iuryl.yellowpeppertest.transfer.controller;

import org.iuryl.yellowpeppertest.common.ApplicationException;
import org.iuryl.yellowpeppertest.common.ErrorDTO;
import org.iuryl.yellowpeppertest.transfer.exception.TransferNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TransferNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(TransferNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO insufficientFundsHandler(ApplicationException ex) {
        return new ErrorDTO(ex.getErrorCode(), ex.getMessage());
    }
}