package org.iuryl.mstest.transfers.service.validation;

import org.iuryl.mstest.transfers.client.AccountClient;
import org.iuryl.mstest.transfers.exception.AccountNumberNotFoundException;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestScope
@Component
public class RecipientAccountValidator implements Validator<TransferEntity> {

    private AccountClient accountClient;

    @Override
    public void validate(TransferEntity transfer) {
        log.trace("Validating recipient account for transfer {}", transfer);

        accountClient.getAccountByNumber(transfer.getRecipientAccountNumber())
            .orElseThrow(() -> new AccountNumberNotFoundException(transfer.getRecipientAccountNumber()));
    }
    
}
