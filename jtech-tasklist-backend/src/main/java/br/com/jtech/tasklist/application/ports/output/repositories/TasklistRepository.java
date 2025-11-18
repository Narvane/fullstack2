package br.com.jtech.tasklist.application.ports.output.repositories;

import br.com.jtech.tasklist.application.core.domains.Tasklist;

import java.util.Optional;
import java.util.UUID;

public interface TasklistRepository {

    Optional<Tasklist> findById(UUID id);

    Tasklist save(Tasklist tasklist);

}
