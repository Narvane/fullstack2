package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.Task;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.UUID;

public class CreateTaskUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;

    public CreateTaskUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository, TasklistRepository tasklistRepository) {
        this.outputGateway = outputGateway;
        this.taskRepository = taskRepository;
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TaskInputData data) {
        UUID userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        if (data.getTasklistId() != null) {
            var tasklist = tasklistRepository.findById(UUID.fromString(data.getTasklistId()))
                    .orElseThrow(() -> new RuntimeException("Tasklist not found"));
            if (!tasklist.getUserId().equals(userId)) {
                throw new RuntimeException("Tasklist not found");
            }
        }

        var createdTask = taskRepository.save(
                Task.builder()
                        .title(data.getTitle())
                        .tasklistId(data.getTasklistId())
                        .completed(false)
                        .build()
        );
        outputGateway.exec(
                TaskOutputData.builder()
                        .id(createdTask.getId().toString())
                        .title(createdTask.getTitle())
                        .completed(createdTask.isCompleted())
                        .tasklistId(createdTask.getTasklistId())
                        .build()
        );
    }

}
