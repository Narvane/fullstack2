package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;

import java.util.UUID;

public class DeleteTasklistUseCase implements TasklistInputGateway {

    private final TasklistRepository tasklistRepository;

    public DeleteTasklistUseCase(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TasklistInputData data) {
        tasklistRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(tasklist -> {
                    tasklistRepository.deleteById(UUID.fromString(data.getId()));
                }, () -> {
                    throw new RuntimeException("Tasklist not found");
                });
    }
}

