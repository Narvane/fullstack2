package br.com.jtech.tasklist.adapters.output.repositories;

import br.com.jtech.tasklist.adapters.output.repositories.jpa.TaskJpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.protocols.TaskEntity;
import br.com.jtech.tasklist.application.core.domains.Task;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskJpaAdapter implements TaskRepository {

    private final TaskJpaRepository repository;
    private final TasklistJpaRepository tasklistRepository;

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Task save(Task task) {
        var tasklistEntity = task.getTasklistId() != null
                ? tasklistRepository.findById(UUID.fromString(task.getTasklistId())).orElse(null)
                : null;

        var taskEntity = TaskEntity.builder()
                .id(task.getId())
                .title(task.getTitle())
                .completed(task.isCompleted())
                .tasklist(tasklistEntity)
                .build();

        var persistedEntity = repository.save(taskEntity);
        return toDomain(persistedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<Task> findByTasklistId(UUID tasklistId) {
        return repository.findByTasklistId(tasklistId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Task toDomain(TaskEntity entity) {
        return Task.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .completed(entity.isCompleted())
                .tasklistId(entity.getTasklist() != null ? entity.getTasklist().getId().toString() : null)
                .build();
    }
}

