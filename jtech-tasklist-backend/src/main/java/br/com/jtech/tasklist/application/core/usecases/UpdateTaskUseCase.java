package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.UUID;

public class UpdateTaskUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;

    public UpdateTaskUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository, TasklistRepository tasklistRepository) {
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

                    if (data.getTitle() != null) {
                        task.setTitle(data.getTitle());
                    }
                    if (data.getTasklistId() != null) {
                        // Validate new tasklist ownership
                        var newTasklist = tasklistRepository.findById(UUID.fromString(data.getTasklistId()))
                                .orElseThrow(() -> new RuntimeException("Tasklist not found"));
                        if (!newTasklist.getUserId().equals(userId)) {
                            throw new RuntimeException("Tasklist not found");
                        }
                        task.setTasklistId(data.getTasklistId());
                    }
                    if (data.getCompleted() != null) {
                        task.setCompleted(data.getCompleted());
                    }

                    var updatedTask = taskRepository.save(task);

                    outputGateway.exec(
                            TaskOutputData.builder()
                                    .id(updatedTask.getId().toString())
                                    .title(updatedTask.getTitle())
                                    .completed(updatedTask.isCompleted())
                                    .tasklistId(updatedTask.getTasklistId())
                                    .build()
                    );
                }, () -> {
                    throw new RuntimeException("Task not found");
                });
    }
}

