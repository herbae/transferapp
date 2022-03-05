package org.iuryl.mstest.transfers.service.validation;

import org.iuryl.mstest.transfers.dto.NewTransferDto;
import org.iuryl.mstest.transfers.exception.CurrencyNotAcceptedException;
import org.iuryl.mstest.transfers.model.Currency;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScope
@Component
public class TransferCurrencyValidator implements Validator<NewTransferDto> {

    @Override
    public void validate(NewTransferDto transfer) {
        log.trace("Validating currency for transfer {}", transfer);

        try {
            Currency currency = Currency.valueOf(transfer.getCurrencyId());

            if (!currency.equals(Currency.USD)) {
                //only USD at this time
                throw new CurrencyNotAcceptedException(transfer.getCurrencyId());
            }
        } catch (IllegalArgumentException ex) {
            throw new CurrencyNotAcceptedException(transfer.getCurrencyId());
        }
    }
    
}
