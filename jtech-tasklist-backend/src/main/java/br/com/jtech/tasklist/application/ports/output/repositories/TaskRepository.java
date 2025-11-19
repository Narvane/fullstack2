package br.com.jtech.tasklist.application.ports.output.repositories;

import br.com.jtech.tasklist.application.core.domains.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {

    Optional<Task> findById(UUID id);

    Task save(Task task);

    void deleteById(UUID id);

    List<Task> findByTasklistId(UUID tasklistId);

    List<Task> findAll();

    boolean existsByTasklistIdAndTitle(UUID tasklistId, String title);

}

