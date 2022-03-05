package org.iuryl.mstest.accounts.dto;

import java.math.BigDecimal;
import java.util.UUID;

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
}
