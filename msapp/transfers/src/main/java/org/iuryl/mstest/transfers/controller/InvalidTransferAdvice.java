package org.iuryl.mstest.transfers.controller;

import org.iuryl.mstest.common.ApplicationException;
import org.iuryl.mstest.common.ErrorDTO;
import org.iuryl.mstest.transfers.exception.AccountNumberNotFoundException;
import org.iuryl.mstest.transfers.exception.CurrencyNotAcceptedException;
import org.iuryl.mstest.transfers.exception.InsufficientFundsException;
import org.iuryl.mstest.transfers.exception.MaxLimitTransferPerDayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidTransferAdvice {

    @ResponseBody
    @ExceptionHandler(value = {InsufficientFundsException.class,
            CurrencyNotAcceptedException.class,
            MaxLimitTransferPerDayException.class,
            AccountNumberNotFoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handler(ApplicationException ex) {
        return new ErrorDTO(ex.getErrorCode(), ex.getMessage());
    }
}