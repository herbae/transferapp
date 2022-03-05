package org.iuryl.yellowpeppertest;

import java.math.BigDecimal;

import org.iuryl.yellowpeppertest.account.model.Account;
import org.iuryl.yellowpeppertest.account.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(AccountRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Account(new BigDecimal("5000"))));
            log.info("Preloading " + repository.save(new Account(new BigDecimal("10000"))));
        };
    }
}
