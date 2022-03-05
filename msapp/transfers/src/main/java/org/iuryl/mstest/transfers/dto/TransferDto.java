package org.iuryl.mstest.transfers.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.iuryl.mstest.transfers.model.TransferEntity;
import org.iuryl.mstest.transfers.model.TransferState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    
    private UUID id;
    private String originAccountNumber;
    private String recipientAccountNumber;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private String currency;
    private TransferState state;

    public TransferDto(TransferEntity transferEntity) {
        this.id = transferEntity.getId();
        this.originAccountNumber = transferEntity.getOriginAccountNumber();
        this.recipientAccountNumber = transferEntity.getRecipientAccountNumber();
        this.amount = transferEntity.getAmount();
        this.description = transferEntity.getDescription();
        this.date = transferEntity.getDate();
        this.currency = transferEntity.getCurrency().toString();
        this.state = transferEntity.getState();
    }
}
