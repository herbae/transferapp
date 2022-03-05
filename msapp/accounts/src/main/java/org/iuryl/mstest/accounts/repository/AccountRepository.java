package org.iuryl.mstest.accounts.repository;

import java.util.Collection;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.iuryl.mstest.accounts.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    
    Collection<AccountEntity> findAllByUserId(Long userId);

    Optional<AccountEntity> findByNumber(String number);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Collection<AccountEntity> findByNumberIn(Collection<String> number);
}
