package org.iuryl.mstest.accounts.dto;

import java.math.BigDecimal;

import org.iuryl.mstest.accounts.model.AccountEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private Long userId;
    private String number;
    private BigDecimal balance;

    public AccountDto(AccountEntity accountEntity) {
        this.id = accountEntity.getId();
        this.userId = accountEntity.getUserId();
        this.number = accountEntity.getNumber();
        this.balance = accountEntity.getBalance();
    }
    
}
