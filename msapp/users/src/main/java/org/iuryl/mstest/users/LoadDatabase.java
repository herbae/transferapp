package org.iuryl.mstest.users;

import org.iuryl.mstest.users.model.UserEntity;
import org.iuryl.mstest.users.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LoadDatabase {

    @Bean
    public CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new UserEntity(
                "Iury Lazoski", "iurylazoski@mstest.org")));
            log.info("Preloading " + repository.save(new UserEntity(
                "John Doe", "johndoe@other.com")));
        };
    }
}
