package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.UUID;

public class DeleteTasklistUseCase implements TasklistInputGateway {

    private final TasklistRepository tasklistRepository;

    public DeleteTasklistUseCase(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TasklistInputData data) {
        UUID userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        tasklistRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(tasklist -> {
                    if (!tasklist.getUserId().equals(userId)) {
                        throw new RuntimeException("Tasklist not found");
                    }
                    tasklistRepository.deleteById(UUID.fromString(data.getId()));
                }, () -> {
                    throw new RuntimeException("Tasklist not found");
                });
    }
}

