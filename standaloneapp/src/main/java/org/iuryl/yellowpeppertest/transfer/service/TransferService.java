package org.iuryl.yellowpeppertest.transfer.service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import javax.transaction.Transactional;

import org.iuryl.yellowpeppertest.account.exception.AccountNotFoundException;
import org.iuryl.yellowpeppertest.account.model.Account;
import org.iuryl.yellowpeppertest.account.repository.AccountRepository;
import org.iuryl.yellowpeppertest.transfer.dto.NewTransferDTO;
import org.iuryl.yellowpeppertest.transfer.dto.TransferProcessedDTO;
import org.iuryl.yellowpeppertest.transfer.exception.CurrencyNotAcceptedException;
import org.iuryl.yellowpeppertest.transfer.exception.MaxLimitTransferPerDayException;
import org.iuryl.yellowpeppertest.transfer.model.Currency;
import org.iuryl.yellowpeppertest.transfer.model.Transfer;
import org.iuryl.yellowpeppertest.transfer.model.TransferState;
import org.iuryl.yellowpeppertest.transfer.repository.TransferRepository;
import org.springframework.stereotype.Service;

@Service
public class TransferService {

    private final static int MAX_TRANSFERS_PER_USER = 3;

    private AccountRepository accountRepository;
    private TransferRepository transferRepository;
    private TaxService taxService;
    private ExchangeService exchangeService;

    public TransferService(AccountRepository accountRepository,
                TransferRepository transferRepository, TaxService taxService,
                ExchangeService exchangeService) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
        this.taxService = taxService;
        this.exchangeService = exchangeService;
    }

    public Transfer validateAndSave(NewTransferDTO dto, String userName) {

        Account fromAccount = accountRepository.findById(dto.fromAccountId)
            .orElseThrow(() -> new AccountNotFoundException(dto.fromAccountId));

        Account toAccount = accountRepository.findById(dto.toAccountId)
            .orElseThrow(() -> new AccountNotFoundException(dto.toAccountId));

        Currency currency = Currency.getCurrencyById(dto.currencyId);

        if (!currency.equals(Currency.USD)) {
            //only USD at this time
            throw new CurrencyNotAcceptedException(dto.currencyId);
        }

        Transfer transfer = new Transfer(fromAccount, toAccount, dto.amount, currency,
                dto.description, userName);

        transfer.setTax(taxService.calculateTax(transfer.getAmount()));

        transferRepository.save(transfer);

        return transfer;
    }

    @Transactional
    public Transfer process(Transfer transfer) {
        transfer.process();
        return transfer;
    }

    public TransferProcessedDTO toOutputDTO(Transfer transfer) {
        BigDecimal amountInCAD = exchangeService.convert(
                transfer.getAmount(), transfer.getCurrency(), Currency.CAD);

        return new TransferProcessedDTO(transfer.getId(), transfer.getTax(), amountInCAD);
    }

    public void checkTransferSucceded(Transfer transfer) {
        if (!transfer.getState().equals(TransferState.PROCESSED)) {
            //error in transfer
            transfer.error();
            transferRepository.save(transfer);
        }
    }

    public void verifyUserLimit(String userName) {
        Collection<Transfer> transfersToday = transferRepository
            .findValidTransfersByUserNameAndDate(userName, LocalDate.now());

        if (transfersToday.size() >= MAX_TRANSFERS_PER_USER) {
            throw new MaxLimitTransferPerDayException(userName);
        }
    }
    
}
