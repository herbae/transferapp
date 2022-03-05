package org.iuryl.yellowpeppertest.transfer.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.iuryl.yellowpeppertest.account.model.Account;
import org.iuryl.yellowpeppertest.transfer.exception.InsufficientFundsException;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
public class Transfer extends AbstractAggregateRoot<Transfer> {
    
    private @Id @GeneratedValue UUID id;
    private @ManyToOne Account fromAccount;
    private @ManyToOne Account toAccount;
    private BigDecimal amount;
    private BigDecimal tax;
    private Currency currency;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransferState state;
    private String userName;
    private LocalDate date;

    public Transfer() {}

    public Transfer(Account fromAccount, Account toAccount, BigDecimal amount,
            Currency currency, String description, String userName) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.userName = userName;
        this.state = TransferState.PENDING;
        this.date = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Account getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }
    public Account getToAccount() {
        return toAccount;
    }
    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public TransferState getState() {
        return state;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void process() {

        BigDecimal totalDebit = this.amount.add(this.tax);

        if (fromAccount.getBalance().compareTo(totalDebit) < 0) {
            throw new InsufficientFundsException(this);
        }

        fromAccount.withdraw(totalDebit);
        toAccount.deposit(this.amount);

        this.state = TransferState.PROCESSED;
    }

    public void error() {
        if (this.state.equals(TransferState.PENDING)) {
            this.state = TransferState.ERROR;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transfer other = (Transfer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Transfer [amount=" + amount + ", fromAccount=" + fromAccount + ", id=" + id + ", state=" + state
                + ", toAccount=" + toAccount + "]";
    }
}
