package org.iuryl.yellowpeppertest.transfer.controller;

import java.security.Principal;
import java.util.UUID;

import javax.validation.Valid;

import org.iuryl.yellowpeppertest.transfer.dto.NewTransferDTO;
import org.iuryl.yellowpeppertest.transfer.dto.TransferDTO;
import org.iuryl.yellowpeppertest.transfer.dto.TransferProcessedDTO;
import org.iuryl.yellowpeppertest.transfer.exception.TransferNotFoundException;
import org.iuryl.yellowpeppertest.transfer.model.Transfer;
import org.iuryl.yellowpeppertest.transfer.repository.TransferRepository;
import org.iuryl.yellowpeppertest.transfer.service.TransferService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private TransferService service;
    private TransferRepository repository;
    private TransferModelAssembler assembler;

    public TransferController(TransferService service, TransferRepository repository,
            TransferModelAssembler assembler) {
        this.service = service;
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/transfers")
    public TransferProcessedDTO newTransfer(@Valid @RequestBody NewTransferDTO newTransfer,
            Principal principal) {
        service.verifyUserLimit(principal.getName());

        Transfer transfer = service.validateAndSave(newTransfer, principal.getName());

        try {
            transfer = service.process(transfer);
        } finally {
            service.checkTransferSucceded(transfer);
        }

        return service.toOutputDTO(transfer);
    }

    // Single item
    @GetMapping("/transfers/{id}")
    public EntityModel<TransferDTO> one(@PathVariable UUID id) {

        Transfer account = repository.findById(id)
                .orElseThrow(() -> new TransferNotFoundException(id));

        return assembler.toModel(account);
    }
}
