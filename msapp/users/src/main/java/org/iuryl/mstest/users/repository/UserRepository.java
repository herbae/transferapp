package org.iuryl.mstest.users.repository;

import java.util.Optional;

import org.iuryl.mstest.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    
    Optional<UserEntity> findByUsername(String username);
}