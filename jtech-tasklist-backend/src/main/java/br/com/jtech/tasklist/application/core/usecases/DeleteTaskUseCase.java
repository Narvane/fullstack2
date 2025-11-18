package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.UUID;

public class DeleteTaskUseCase implements TaskInputGateway {

    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository, TasklistRepository tasklistRepository) {
        this.taskRepository = taskRepository;
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TaskInputData data) {
        UUID userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        taskRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(task -> {
                    // Validate tasklist ownership
                    if (task.getTasklistId() != null) {
                        var tasklist = tasklistRepository.findById(UUID.fromString(task.getTasklistId()))
                                .orElseThrow(() -> new RuntimeException("Task not found"));
                        if (!tasklist.getUserId().equals(userId)) {
                            throw new RuntimeException("Task not found");
                        }
                    }
                    taskRepository.deleteById(UUID.fromString(data.getId()));
                }, () -> {
                    throw new RuntimeException("Task not found");
                });
    }
}

