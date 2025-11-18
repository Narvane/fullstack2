package br.com.jtech.tasklist.adapters.output;

import br.com.jtech.tasklist.adapters.output.repositories.jpa.TasklistJpaRepository;
import br.com.jtech.tasklist.adapters.output.repositories.protocols.TasklistEntity;
import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.ports.output.CreateTasklistOutputGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class CreateTasklistAdapter implements CreateTasklistOutputGateway {

    private final TasklistJpaRepository repository;

    @Override
    public Tasklist create(Tasklist tasklist) {
        var persistedTasklist = this.repository.save(TasklistEntity.builder()
                .title(tasklist.getTitle())
                .tasks(new ArrayList<>())
                .build()
        );
        return Tasklist.builder()
                .id(persistedTasklist.getId())
                .build();
    }

}