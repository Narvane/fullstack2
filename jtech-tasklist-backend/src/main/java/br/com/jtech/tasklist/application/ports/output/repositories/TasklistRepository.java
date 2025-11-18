package br.com.jtech.tasklist.application.ports.output.repositories;

import br.com.jtech.tasklist.application.core.domains.Tasklist;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TasklistRepository {

    Optional<Tasklist> findById(UUID id);

    Tasklist save(Tasklist tasklist);

    void deleteById(UUID id);

    List<Tasklist> findAll();

    List<Tasklist> findByUserId(UUID userId);

}
