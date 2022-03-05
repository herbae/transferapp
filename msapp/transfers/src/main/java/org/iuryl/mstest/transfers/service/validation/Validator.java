package org.iuryl.mstest.transfers.service.validation;

public interface Validator<T> {
    void validate(T entity);
}
