package org.iuryl.mstest.accounts.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class AccountEntity {
    
    private @Id @GeneratedValue Long id;

    @NonNull
    private Long userId;
    @NonNull
    private String number;
    @NonNull
    private BigDecimal balance;

    public AccountEntity(String number) {
        this.number = number;
    }

    public void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
