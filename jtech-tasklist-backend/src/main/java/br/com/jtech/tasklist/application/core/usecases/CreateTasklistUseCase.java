package br.com.jtech.tasklist.application.core.usecases;


import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ConflictException;
import br.com.jtech.tasklist.config.infra.exceptions.UnauthorizedException;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.UUID;

public class CreateTasklistUseCase implements TasklistInputGateway {

    private final TasklistOutputGateway outputGateway;
    private final TasklistRepository tasklistRepository;

    public CreateTasklistUseCase(TasklistOutputGateway outputGateway, TasklistRepository tasklistRepository) {
        this.outputGateway = outputGateway;
        this.tasklistRepository = tasklistRepository;
    }

    public void exec(TasklistInputData data) {
        UUID userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        // Check for duplicate title
        if (tasklistRepository.existsByUserIdAndTitle(userId, data.getTitle())) {
            throw new ConflictException("A tasklist with this title already exists");
        }

        var createdTask = tasklistRepository.save(
                Tasklist.builder()
                        .title(data.getTitle())
                        .userId(userId)
                        .build()
        );
        outputGateway.exec(
                TasklistOutputData.builder()
                        .id(createdTask.getId().toString())
                        .title(createdTask.getTitle())
                        .build()
        );
    }

}