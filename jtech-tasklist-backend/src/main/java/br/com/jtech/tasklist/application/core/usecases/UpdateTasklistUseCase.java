package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ConflictException;
import br.com.jtech.tasklist.config.infra.exceptions.ResourceNotFoundException;
import br.com.jtech.tasklist.config.infra.exceptions.UnauthorizedException;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.UUID;

public class UpdateTasklistUseCase implements TasklistInputGateway {

    private final TasklistOutputGateway outputGateway;
    private final TasklistRepository tasklistRepository;

    public UpdateTasklistUseCase(TasklistOutputGateway outputGateway,
                                 TasklistRepository tasklistRepository) {
        this.outputGateway = outputGateway;
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TasklistInputData data) {
        UUID userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        tasklistRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(tasklist -> {
                    if (!tasklist.getUserId().equals(userId)) {
                        throw new ResourceNotFoundException("Tasklist not found");
                    }
                    if (!tasklist.getTitle().equals(data.getTitle()) && 
                        tasklistRepository.existsByUserIdAndTitle(userId, data.getTitle())) {
                        throw new ConflictException("A tasklist with this title already exists");
                    }
                    tasklist.setTitle(data.getTitle());

                    var updatedTask = tasklistRepository.save(tasklist);

                    outputGateway.exec(
                            TasklistOutputData.builder()
                                    .id(updatedTask.getId().toString())
                                    .title(updatedTask.getTitle())
                            .build()
                    );
                }, () -> {
                    throw new ResourceNotFoundException("Tasklist not found");
                });
    }

}
