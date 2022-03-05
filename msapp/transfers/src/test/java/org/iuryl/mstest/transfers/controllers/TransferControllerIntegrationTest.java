package org.iuryl.mstest.transfers.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.iuryl.mstest.transfers.controller.TransferController;
import org.iuryl.mstest.transfers.dto.TransferDto;
import org.iuryl.mstest.transfers.model.Currency;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.iuryl.mstest.transfers.model.TransferState;
import org.iuryl.mstest.transfers.repository.TransferRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TransfersControllerIntegrationTest {
    
    @Autowired
    private TransferController controller;

    @Autowired
    private TransferRepository repository;

    @Test
    void retrieveAllTransfers() {
        repository.save(transferOkToSave());

        List<TransferDto> transfers = controller.all();

        assertEquals(1, transfers.size());
        TransferDto dto = transfers.get(0);
        assertNotNull(dto.getId());
        assertEquals(BigDecimal.TEN.setScale(2), dto.getAmount());
        assertEquals(TransferState.PENDING, dto.getState());
    }

    private TransferEntity transferOkToSave() {
        TransferEntity transfer = new TransferEntity();
        transfer.setAmount(BigDecimal.TEN);
        transfer.setOriginAccountNumber("origin");
        transfer.setRecipientAccountNumber("recipient");
        transfer.setTax(BigDecimal.ONE);
        transfer.setCurrency(Currency.USD);
        transfer.setState(TransferState.PENDING);
        return transfer;
    }
}
