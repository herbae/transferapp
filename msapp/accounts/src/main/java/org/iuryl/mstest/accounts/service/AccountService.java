package org.iuryl.mstest.accounts.service;


import java.util.Arrays;
import java.util.Collection;

import org.iuryl.mstest.accounts.dto.ProcessTransferDto;
import org.iuryl.mstest.accounts.exception.AccountNumberNotFoundException;
import org.iuryl.mstest.accounts.exception.InsufficientFundsException;
import org.iuryl.mstest.accounts.model.AccountEntity;
import org.iuryl.mstest.accounts.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AccountService {
    
    private AccountRepository repository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void transfer(ProcessTransferDto dto) {

        //one select to prevent deadlocks (would be better to use a queue instead of this)
        Collection<AccountEntity> accounts = repository.findByNumberIn(Arrays.asList(
            dto.getOriginAccountNumber(), dto.getRecipientAccountNumber()
        ));

        AccountEntity originAccount = accounts.stream()
            .filter(ac -> ac.getNumber().equals(dto.getOriginAccountNumber()))
            .findFirst()
            .orElseThrow(() -> new AccountNumberNotFoundException(dto.getOriginAccountNumber()));

        AccountEntity recipientAccount = accounts.stream()
            .filter(ac -> ac.getNumber().equals(dto.getRecipientAccountNumber()))
            .findFirst()
            .orElseThrow(() -> new AccountNumberNotFoundException(dto.getRecipientAccountNumber()));

        if (originAccount.getBalance().compareTo(dto.getAmountToWithdraw()) < 0) {
            throw new InsufficientFundsException(originAccount.getNumber());
        }

        log.info("------------ cut here -----------------");
        log.trace("Balance in origin account {}: {}", originAccount.getNumber(),
            originAccount.getBalance());

        originAccount.withdraw(dto.getAmountToWithdraw());

        log.trace("Balance in origin account after withdrawal {}: {}", originAccount.getNumber(),
            originAccount.getBalance());

        log.trace("Balance in recipient account {}: {}", recipientAccount.getNumber(),
            recipientAccount.getBalance());

        recipientAccount.deposit(dto.getAmountToDeposit());

        log.trace("Balance in recipient account after deposit {}: {}", recipientAccount.getNumber(),
            recipientAccount.getBalance());
        log.info("------------ cut here -----------------");
    }
}
