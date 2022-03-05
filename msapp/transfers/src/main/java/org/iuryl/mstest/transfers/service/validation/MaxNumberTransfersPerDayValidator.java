package org.iuryl.mstest.transfers.service.validation;

import java.time.LocalDate;
import java.util.Collection;

import org.iuryl.mstest.common.UserInfo;
import org.iuryl.mstest.transfers.dto.NewTransferDto;
import org.iuryl.mstest.transfers.exception.MaxLimitTransferPerDayException;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.iuryl.mstest.transfers.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestScope
@Component
public class MaxNumberTransfersPerDayValidator implements Validator<NewTransferDto> {

    @Value("${org.iuryl.mstest.maxTranfersPerUser:3}")
    private String MAX_TRANSFERS_PER_USER;

    private final TransferRepository repository;
    private final UserInfo userId;

    @Override
    public void validate(NewTransferDto transfer) {
        log.trace("Validating max transfers per day for user {}", userId.getUserId());
        Collection<TransferEntity> transfersToday = repository
            .findValidTransfersByUserNameAndDate(userId.getUserId(), LocalDate.now());

        if (transfersToday.size() >= Integer.parseInt(MAX_TRANSFERS_PER_USER)) {
            throw new MaxLimitTransferPerDayException(userId.getUserId());
        }
    }
    
}
