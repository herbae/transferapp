package org.iuryl.mstest.transfers.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferProcessedDto {
    private UUID id;
    private BigDecimal taxCollected;
    private BigDecimal amountInCAD;

}
