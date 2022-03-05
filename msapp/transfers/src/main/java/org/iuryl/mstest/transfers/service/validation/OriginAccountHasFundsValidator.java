package org.iuryl.mstest.transfers.service.validation;

import java.math.BigDecimal;
import java.util.Optional;

import org.iuryl.mstest.transfers.client.AccountClient;
import org.iuryl.mstest.transfers.dto.AccountDto;
import org.iuryl.mstest.transfers.exception.InsufficientFundsException;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RequestScope
@Component
public class OriginAccountHasFundsValidator implements Validator<TransferEntity> {

    private AccountClient accountClient;

    @Override
    public void validate(TransferEntity transfer) {
        log.trace("Validating origin account has funds for transfer {}", transfer);

        //check if origin account is valid
        Optional<AccountDto> account = accountClient.getAccountByNumber(transfer.getOriginAccountNumber());

        if (account.isEmpty()) {
            return;
        }
            
        BigDecimal amountToWithdraw = transfer.getAmountToWithdraw();

        if (account.get().getBalance().compareTo(amountToWithdraw) < 0) {
            throw new InsufficientFundsException(transfer.getOriginAccountNumber());
        }
    }
    
}
