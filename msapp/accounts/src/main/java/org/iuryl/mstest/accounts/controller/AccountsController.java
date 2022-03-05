package org.iuryl.mstest.accounts.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.iuryl.mstest.accounts.dto.AccountDto;
import org.iuryl.mstest.accounts.dto.ProcessTransferDto;
import org.iuryl.mstest.accounts.model.AccountEntity;
import org.iuryl.mstest.accounts.repository.AccountRepository;
import org.iuryl.mstest.accounts.service.AccountService;
import org.iuryl.mstest.common.NotAuthorizedException;
import org.iuryl.mstest.common.UserInfo;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class AccountsController {
    
	private AccountRepository repository;
	private AccountService service;
	private UserInfo userInfo;

	@GetMapping("/public/accounts/users/{userId}")
    public List<AccountDto> allByUserId(@PathVariable Long userId) {
		log.info("Fetching accounts for user " + userId);

		if (userInfo.getUserId() != userId.longValue()) {
			throw new NotAuthorizedException(userInfo.getUserId(),
				String.format("Accounts for userId %d", userId));
		}

        return repository.findAllByUserId(userId).stream()
            .map(AccountDto::new)
            .collect(Collectors.toList());
    }

	@GetMapping("/public/accounts/number/{number}")
	public Optional<AccountDto> accountByNumber(@PathVariable String number) {
		return repository.findOne(Example.of(new AccountEntity(number)))
			//will return 404 if user does not own this account number
			.filter(account -> account.getUserId().equals(userInfo.getUserId()))
			.map(AccountDto::new);
	}

	//this is for internal use only
	@GetMapping("/accounts/number/{number}")
	public Optional<AccountDto> accountByNumberInternal(@PathVariable String number) {
		return repository.findOne(Example.of(new AccountEntity(number)))
			.map(AccountDto::new);
	}

	@PostMapping("/accounts/transfer")
	public String newTransfer(@RequestBody ProcessTransferDto dto) {
		log.info("Processing transfer: {}", dto.getTransferId());

		service.transfer(dto);

		return "ok";
	}
}
