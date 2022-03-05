package org.iuryl.mstest.transfers.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.iuryl.mstest.transfers.client.AccountClient;
import org.iuryl.mstest.transfers.client.TaxClient;
import org.iuryl.mstest.transfers.dto.RequestTaxDto;
import org.iuryl.mstest.transfers.dto.TaxDto;
import org.iuryl.mstest.transfers.exception.AccountNumberNotFoundException;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.iuryl.mstest.transfers.model.TransferState;
import org.iuryl.mstest.transfers.repository.TransferRepository;
import org.iuryl.mstest.transfers.service.validation.OriginAccountHasFundsValidator;
import org.iuryl.mstest.transfers.service.validation.OriginAccountValidator;
import org.iuryl.mstest.transfers.service.validation.RecipientAccountValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TransferServiceUnitTest {

    @InjectMocks
    private TransferService service;

    @Mock
    private TransferRepository repository;
    @Mock
    private AccountClient accountClient;
    @Mock
    private TaxClient taxClient;
    @Mock
    private OriginAccountValidator originAccountValidator;
    @Mock
    private RecipientAccountValidator recipientAccountValidator;
    @Mock
    private OriginAccountHasFundsValidator originAccountHasFundsValidator;

    private AutoCloseable testClass;

    @BeforeEach
    void setup() {
        this.testClass = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void cleanup() throws Exception {
        testClass.close();
    }

    @Test
    void validingTransferWithErrorShouldChangeStateOfTransfer() {
        doThrow(AccountNumberNotFoundException.class)
            .when(originAccountValidator).validate(any(TransferEntity.class));

        TransferEntity transfer = newTransferOkWithTax();

        try {
            assertEquals(TransferState.PENDING, transfer.getState());
            service.validateAccounts(transfer);
            fail("Should have thrown exception.");
        } catch(AccountNumberNotFoundException ex) {
            assertEquals(TransferState.ERROR, transfer.getState());
        }
    }

    @Test
    void transferSavedShouldApplyAndSaveTax() {
        when(repository.save(any(TransferEntity.class)))
            .thenAnswer((t) -> (TransferEntity) t.getArguments()[0]);

        BigDecimal amount = BigDecimal.TEN;
        BigDecimal calculatedTax = BigDecimal.ONE;

        when(taxClient.applyTax(new RequestTaxDto(amount)))
            .thenReturn(new TaxDto(amount, new BigDecimal("0.1"), calculatedTax));

        TransferEntity transfer = service.save(newTransferOk());

        assertEquals(calculatedTax, transfer.getTax());
    }

    @Test
    void transferProcessedShouldChangeStateOfTransfer() {
        when(repository.save(any(TransferEntity.class)))
            .thenAnswer((t) -> (TransferEntity) t.getArguments()[0]);

        TransferEntity transfer = newTransferOkWithTax();

        TransferEntity transferProcessed = service.process(transfer);

        assertEquals(TransferState.PROCESSED, transferProcessed.getState());
    }

    private TransferEntity newTransferOk() {
        TransferEntity transfer = new TransferEntity();
        transfer.setId(UUID.randomUUID());
        transfer.setAmount(BigDecimal.TEN);
        transfer.setOriginAccountNumber("origin");
        transfer.setRecipientAccountNumber("recipient");
        transfer.setState(TransferState.PENDING);
        return transfer;
    }

    private TransferEntity newTransferOkWithTax() {
        TransferEntity transfer = newTransferOk();
        transfer.setTax(BigDecimal.ONE);
        return transfer;
    }
}
