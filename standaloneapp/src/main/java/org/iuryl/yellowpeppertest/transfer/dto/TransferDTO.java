package org.iuryl.yellowpeppertest.transfer.dto;

import java.math.BigDecimal;
import java.util.UUID;

import org.iuryl.yellowpeppertest.transfer.model.Transfer;
import org.iuryl.yellowpeppertest.transfer.model.TransferState;

public class TransferDTO {
    public UUID id;
    public Long fromAccountId;
    public Long toAccountId;
    public TransferState state;
    public BigDecimal tax;
    public String currency;

    public TransferDTO(Transfer transfer) {
        this.id = transfer.getId();
        this.fromAccountId = transfer.getFromAccount().getId();
        this.toAccountId = transfer.getToAccount().getId();
        this.state = transfer.getState();
        this.tax = transfer.getTax();
        this.currency = transfer.getCurrency().toString();
    }
}
