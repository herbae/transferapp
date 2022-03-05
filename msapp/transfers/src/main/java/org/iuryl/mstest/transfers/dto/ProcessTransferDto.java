package org.iuryl.mstest.transfers.dto;

import java.math.BigDecimal;
import java.util.UUID;

import org.iuryl.mstest.transfers.model.TransferEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProcessTransferDto {
    
    private UUID transferId;
    private String originAccountNumber;
    private String recipientAccountNumber;
    private BigDecimal amountToWithdraw;
    private BigDecimal amountToDeposit;

    public ProcessTransferDto(TransferEntity transfer) {
        this.transferId = transfer.getId();
        this.originAccountNumber = transfer.getOriginAccountNumber();
        this.recipientAccountNumber = transfer.getRecipientAccountNumber();
        this.amountToWithdraw = transfer.getAmountToWithdraw();
        this.amountToDeposit = transfer.getAmountToDeposit();
    }
}
