package org.iuryl.yellowpeppertest.account.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.iuryl.yellowpeppertest.account.exception.AccountNotFoundException;
import org.iuryl.yellowpeppertest.account.model.Account;
import org.iuryl.yellowpeppertest.account.repository.AccountRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    private AccountRepository repository;
    private AccountModelAssembler assembler;

    public AccountController(AccountRepository repository,
            AccountModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/accounts")
    public CollectionModel<EntityModel<Account>> all() {
        List<EntityModel<Account>> accounts = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(accounts,
            linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    @PostMapping("/accounts")
    public ResponseEntity<?> newAccount(@RequestBody Account newAccount) {
        EntityModel<Account> entityModel = assembler.toModel(repository.save(newAccount));

        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    // Single item
    @GetMapping("/accounts/{id}")
    public EntityModel<Account> one(@PathVariable Long id) {

        Account account = repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        return assembler.toModel(account);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}