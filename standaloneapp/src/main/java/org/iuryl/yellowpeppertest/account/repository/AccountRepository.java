package org.iuryl.yellowpeppertest.account.repository;

import org.iuryl.yellowpeppertest.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
