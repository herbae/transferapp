package org.iuryl.mstest.transfers.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.iuryl.mstest.transfers.dto.NewTransferDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Entity
public class TransferEntity {
    
    private @Id @GeneratedValue UUID id;

    private Long userId;
    private String originAccountNumber;
    private String recipientAccountNumber;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private BigDecimal tax;
    @Enumerated(EnumType.STRING)
    private TransferState state;

    public TransferEntity(NewTransferDto newTransfer, long userId) {
        this.userId = userId;
        this.originAccountNumber = newTransfer.getOriginAccountNumber();
        this.recipientAccountNumber = newTransfer.getRecipientAccountNumber();
        this.amount = newTransfer.getAmount();
        this.description = newTransfer.getDescription();
        this.currency = Currency.valueOf(newTransfer.getCurrencyId());
        this.date = LocalDate.now();
        this.state = TransferState.PENDING;
    }

    public BigDecimal getAmountToWithdraw() {
        return this.amount.add(this.tax).setScale(2);
    }

    public BigDecimal getAmountToDeposit() {
        return this.amount.setScale(2);
    }

    public void processed() {
        this.state = TransferState.PROCESSED;
    }

    public void error() {
        this.state = TransferState.ERROR;
    }
}
