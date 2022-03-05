package org.iuryl.mstest.users.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.iuryl.mstest.users.client.AccountClient;
import org.iuryl.mstest.users.dto.AccountDto;
import org.iuryl.mstest.users.dto.UserDto;
import org.iuryl.mstest.users.dto.UserForAuthDto;
import org.iuryl.mstest.users.exception.UserNotFoundException;
import org.iuryl.mstest.users.model.UserEntity;
import org.iuryl.mstest.users.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@RestController
public class UsersController {
    
    private UserRepository repository;
    private AccountClient accountClient;

    @GetMapping("/users")
    public List<UserDto> all() {
        return repository.findAll().stream()
            .map(UserDto::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto one(@PathVariable Long id) {
        log.info("user by id was called");

        UserEntity user = repository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

        List<AccountDto> accounts = accountClient.getAccountsByUserId(id);

        return new UserDto(user.getId(), user.getName(), accounts);
    }

    @GetMapping("/users/username/{username}")
    public Optional<UserForAuthDto> oneByUserName(@PathVariable String username) {
        log.info("user by username was called");

        return repository.findByUsername(username)
            .map(UserForAuthDto::new);
    }

}
