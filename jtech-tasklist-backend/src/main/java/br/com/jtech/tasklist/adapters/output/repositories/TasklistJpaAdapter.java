package br.com.jtech.tasklist.adapters.output.repositories;

import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.protocols.TasklistEntity;
import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class TasklistJpaAdapter implements TasklistRepository {

    private final TasklistJpaRepository repository;

    @Override
    public Optional<Tasklist> findById(UUID id) {
        return repository.findById(id).map(tasklistEntity ->
                Tasklist.builder()
                        .id(tasklistEntity.getId())
                        .title(tasklistEntity.getTitle())
                        .build());
    }

    @Override
    public Tasklist save(Tasklist tasklist) {
        var persistedTasklistEntity =  repository.save(
                TasklistEntity.builder()
                        .id(tasklist.getId())
                        .title(tasklist.getTitle())
                        .build()
        );
        return Tasklist.builder()
                .id(persistedTasklistEntity.getId())
                .title(persistedTasklistEntity.getTitle())
                .build();
    }

}
