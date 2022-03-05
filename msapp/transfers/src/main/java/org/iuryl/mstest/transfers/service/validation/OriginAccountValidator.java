package org.iuryl.mstest.transfers.service.validation;

import org.iuryl.mstest.common.NotAuthorizedException;
import org.iuryl.mstest.common.UserInfo;
import org.iuryl.mstest.transfers.client.AccountClient;
import org.iuryl.mstest.transfers.dto.AccountDto;
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
public class OriginAccountValidator implements Validator<TransferEntity> {

    private AccountClient accountClient;
    private UserInfo userId;

    @Override
    public void validate(TransferEntity transfer) {
        log.trace("Validating origin account for transfer {}", transfer);

        //check if origin account is valid
        AccountDto originAccount = accountClient.getAccountByNumber(transfer.getOriginAccountNumber())
            .orElseThrow(() -> new AccountNumberNotFoundException(transfer.getOriginAccountNumber()));

        //check if user owns origin account
        if (userId.getUserId() != originAccount.getUserId().longValue()) {
            throw new NotAuthorizedException(userId.getUserId(),
                String.format("Withdrawal from account # %s", transfer.getOriginAccountNumber()));
        }
    }
    
}
