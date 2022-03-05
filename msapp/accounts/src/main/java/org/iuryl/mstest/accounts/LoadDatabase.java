package org.iuryl.mstest.accounts;

import java.math.BigDecimal;

import org.iuryl.mstest.accounts.model.AccountEntity;
import org.iuryl.mstest.accounts.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    public CommandLineRunner initDatabase(AccountRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new AccountEntity(1L, "78657", new BigDecimal("5000"))));
            log.info("Preloading " + repository.save(new AccountEntity(2L, "98463", new BigDecimal("15000"))));
            log.info("Preloading " + repository.save(new AccountEntity(2L, "38473", new BigDecimal("2000"))));
        };
    }
}
