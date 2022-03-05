package org.iuryl.yellowpeppertest.transfer.model;

import javax.validation.constraints.NotNull;

import org.iuryl.yellowpeppertest.transfer.exception.CurrencyNotAcceptedException;

public enum Currency {
    USD, CAD;

    public static Currency getCurrencyById(@NotNull String currencyId) {
        try {
            return valueOf(currencyId);
        } catch (IllegalArgumentException ex) {
            throw new CurrencyNotAcceptedException(currencyId);
        }
    }
}
