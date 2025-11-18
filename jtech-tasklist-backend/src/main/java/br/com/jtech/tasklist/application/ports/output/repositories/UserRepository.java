package br.com.jtech.tasklist.application.ports.output.repositories;

import br.com.jtech.tasklist.application.core.domains.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    User save(User user);

    Optional<User> findById(UUID id);

}

