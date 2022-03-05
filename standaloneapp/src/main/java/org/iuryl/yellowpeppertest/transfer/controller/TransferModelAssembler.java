package org.iuryl.yellowpeppertest.transfer.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.iuryl.yellowpeppertest.transfer.dto.TransferDTO;
import org.iuryl.yellowpeppertest.transfer.model.Transfer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TransferModelAssembler
    implements RepresentationModelAssembler<Transfer, EntityModel<TransferDTO>> {

    @Override
    public EntityModel<TransferDTO> toModel(Transfer transfer) {
        return EntityModel.of(new TransferDTO(transfer),
                linkTo(methodOn(TransferController.class).one(transfer.getId())).withSelfRel());
    }

}
