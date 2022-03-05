package org.iuryl.mstest.transfers.service;

import java.math.BigDecimal;

import org.iuryl.mstest.common.ApplicationException;
import org.iuryl.mstest.common.UserInfo;
import org.iuryl.mstest.transfers.client.AccountClient;
import org.iuryl.mstest.transfers.client.ExchangeClient;
import org.iuryl.mstest.transfers.client.TaxClient;
import org.iuryl.mstest.transfers.dto.ExchangeDto;
import org.iuryl.mstest.transfers.dto.ExchangeOutDto;
import org.iuryl.mstest.transfers.dto.NewTransferDto;
import org.iuryl.mstest.transfers.dto.ProcessTransferDto;
import org.iuryl.mstest.transfers.dto.RequestTaxDto;
import org.iuryl.mstest.transfers.dto.TaxDto;
import org.iuryl.mstest.transfers.dto.TransferProcessedDto;
import org.iuryl.mstest.transfers.exception.UnableToCalculateTaxException;
import org.iuryl.mstest.transfers.infra.TrackExecutionTime;
import org.iuryl.mstest.transfers.model.Currency;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.iuryl.mstest.transfers.repository.TransferRepository;
import org.iuryl.mstest.transfers.service.validation.MaxNumberTransfersPerDayValidator;
import org.iuryl.mstest.transfers.service.validation.OriginAccountHasFundsValidator;
import org.iuryl.mstest.transfers.service.validation.OriginAccountValidator;
import org.iuryl.mstest.transfers.service.validation.RecipientAccountValidator;
import org.iuryl.mstest.transfers.service.validation.TransferCurrencyValidator;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class TransferService {

    private TransferRepository repository;

    private AccountClient accountClient;
    private ExchangeClient exchangeClient;
    private TaxClient taxClient;
    private UserInfo userId;

    private OriginAccountValidator originAccountValidator;
    private RecipientAccountValidator recipientAccountValidator;
    private MaxNumberTransfersPerDayValidator maxNumberTransfersPerDayValidator;
    private OriginAccountHasFundsValidator originAccountHasFunds;
    private TransferCurrencyValidator transferCurrencyValidator;

    @TrackExecutionTime
    public TransferEntity validateBasic(NewTransferDto dto) {
        log.debug("Verifying max number of transfers per day: {}", dto);
        maxNumberTransfersPerDayValidator.validate(dto);
        log.debug("Checking currency: {}", dto);
        transferCurrencyValidator.validate(dto);

        return new TransferEntity(dto, userId.getUserId());
    }
    
    @TrackExecutionTime
    public void validateAccounts(TransferEntity transfer) {
        log.info("Validating " + transfer);
        
        try {
            log.debug("Checking if origin account is valid: {}", transfer.getId());
            originAccountValidator.validate(transfer);
            log.debug("Checking if recipient account is valid: {}", transfer.getId());
            recipientAccountValidator.validate(transfer);
            log.debug("Checking if origin account has funds: {}", transfer.getId());
            originAccountHasFunds.validate(transfer);
        } catch (ApplicationException ex) {
            log.warn("Error in transfer: {}, {}", transfer.getId(), ex.getMessage());
            transfer.error();
            repository.save(transfer);
            throw ex;
        }
    }

    @TrackExecutionTime
    public TransferEntity save(TransferEntity transfer) {
        log.info("Saving {} in pending state.", transfer.toString());

        TaxDto taxDto = null;
        try {
            taxDto = taxClient.applyTax(new RequestTaxDto(transfer.getAmount()));
            transfer.setTax(taxDto.getCalculatedTax());
        } catch (FeignException ex) {
            //TODO this is a workaround while we dont have resilience4j
            log.warn("Tax service error {}", ex.getMessage());
            throw new UnableToCalculateTaxException(ex);
        }
                
        return repository.save(transfer);
    }
    
    @TrackExecutionTime
    public TransferEntity process(TransferEntity transfer) {
        
        ProcessTransferDto dto = new ProcessTransferDto(transfer);

        log.info("Processing {} ", transfer.getId());
        accountClient.transfer(dto);
        
        log.info("Processed OK, saving state {} ", transfer.getId());
        transfer.processed();
        return repository.save(transfer);
    }
    
    @TrackExecutionTime
    public TransferProcessedDto toOutputDTO(TransferEntity transfer) {
        
        log.info("Fetching exchange for transfer {} ", transfer.getId());
        ExchangeOutDto dto = null;
        try {
            dto = exchangeClient.convert(
                new ExchangeDto(transfer.getCurrency().toString(),
                Currency.CAD.toString(), transfer.getAmount()));
        } catch (FeignException ex) {
            //TODO this is a workaround while we dont have resilience4j
            log.warn("Exchange service error {}", ex.getMessage());
            dto = new ExchangeOutDto(BigDecimal.ZERO);
        }
            
        log.info("Returning to client transfer {} ", transfer.getId());
        return new TransferProcessedDto(transfer.getId(), transfer.getTax(), dto.getAmount());
    }
}
