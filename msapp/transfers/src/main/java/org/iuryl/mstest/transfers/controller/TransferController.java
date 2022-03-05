package org.iuryl.mstest.transfers.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.iuryl.mstest.common.UserInfo;
import org.iuryl.mstest.transfers.dto.NewTransferDto;
import org.iuryl.mstest.transfers.dto.TransferDto;
import org.iuryl.mstest.transfers.dto.TransferProcessedDto;
import org.iuryl.mstest.transfers.infra.TrackExecutionTime;
import org.iuryl.mstest.transfers.model.TransferEntity;
import org.iuryl.mstest.transfers.repository.TransferRepository;
import org.iuryl.mstest.transfers.service.TransferService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class TransferController {
    
    private TransferRepository repository;
    private TransferService service;
    private UserInfo userInfo;

    @GetMapping("/public/transfers")
    public List<TransferDto> all() {
        return repository.findAll().stream()
            .filter(transfer -> transfer.getUserId().equals(userInfo.getUserId()))
            .map(TransferDto::new)
            .collect(Collectors.toList());
    }

    @TrackExecutionTime
    @PostMapping("/public/transfers")
    public TransferProcessedDto newTransfer(@Valid @RequestBody NewTransferDto newTransfer) {

        log.info("New Transfer arrived: {}", newTransfer.toString());
        TransferEntity transfer = service.validateBasic(newTransfer);

        transfer = service.save(transfer);

        service.validateAccounts(transfer);
        
        transfer = service.process(transfer);
        
        return service.toOutputDTO(transfer);
    }
}
