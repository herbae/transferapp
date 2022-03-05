package org.iuryl.yellowpeppertest.transfer.controller;

import org.iuryl.yellowpeppertest.common.ApplicationException;
import org.iuryl.yellowpeppertest.common.ErrorDTO;
import org.iuryl.yellowpeppertest.transfer.exception.CurrencyNotAcceptedException;
import org.iuryl.yellowpeppertest.transfer.exception.InsufficientFundsException;
import org.iuryl.yellowpeppertest.transfer.exception.MaxLimitTransferPerDayException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadRequestAdvice {

    @ResponseBody
    @ExceptionHandler(value = {InsufficientFundsException.class,
            CurrencyNotAcceptedException.class,
            MaxLimitTransferPerDayException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handler(ApplicationException ex) {
        return new ErrorDTO(ex.getErrorCode(), ex.getMessage());
    }
}