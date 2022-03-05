package org.iuryl.mstest.transfers.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

import org.iuryl.mstest.transfers.model.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransferRepository extends JpaRepository<TransferEntity, UUID> {
    
    @Query("select t from TransferEntity t where t.userId = ?1 and date = ?2 and state <> 'ERROR'")
    Collection<TransferEntity> findValidTransfersByUserNameAndDate(Long userId, LocalDate date);
}