package org.iuryl.yellowpeppertest.transfer.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import org.iuryl.yellowpeppertest.transfer.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {
    
    @Query("select t from Transfer t where t.userName = ?1 and date = ?2 and t.state <> 'ERROR'")
    Collection<Transfer> findValidTransfersByUserNameAndDate(String userName, LocalDate date);
}
