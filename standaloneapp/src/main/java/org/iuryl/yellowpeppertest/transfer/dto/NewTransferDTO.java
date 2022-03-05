package org.iuryl.yellowpeppertest.transfer.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewTransferDTO {

    @NotNull
    public Long fromAccountId;
    @NotNull
    public Long toAccountId;
    @NotNull
    public BigDecimal amount;
    @NotNull
    public String currencyId;
    @NotBlank
    public String description;
}
