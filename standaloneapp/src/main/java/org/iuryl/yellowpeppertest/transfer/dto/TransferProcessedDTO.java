package org.iuryl.yellowpeppertest.transfer.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferProcessedDTO {
    public UUID id;
    public BigDecimal taxCollected;
    public BigDecimal amountInCAD;

    public TransferProcessedDTO(UUID id, BigDecimal taxCollected, BigDecimal amountInCAD) {
        this.id = id;
        this.taxCollected = taxCollected;
        this.amountInCAD = amountInCAD;
    }
}
