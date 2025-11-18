package br.com.jtech.tasklist.adapters.output.repositories.jpa;

import br.com.jtech.tasklist.adapters.output.repositories.protocols.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    
    Optional<UserEntity> findByEmail(String email);
    
}

