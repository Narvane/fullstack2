package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ConflictException;
import br.com.jtech.tasklist.config.infra.exceptions.ResourceNotFoundException;
import br.com.jtech.tasklist.config.infra.exceptions.UnauthorizedException;
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
            throw new UnauthorizedException("User not authenticated");
        }

        taskRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(task -> {
                    if (task.getTasklistId() != null) {
                        var tasklist = tasklistRepository.findById(UUID.fromString(task.getTasklistId()))
                                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
                        if (!tasklist.getUserId().equals(userId)) {
                            throw new ResourceNotFoundException("Task not found");
                        }
                    }

                    if (data.getTitle() != null) {
                        UUID currentTasklistId = task.getTasklistId() != null ? 
                                UUID.fromString(task.getTasklistId()) : null;
                        if (currentTasklistId != null && 
                            !task.getTitle().equals(data.getTitle()) &&
                            taskRepository.existsByTasklistIdAndTitle(currentTasklistId, data.getTitle())) {
                            throw new ConflictException("A task with this title already exists in this tasklist");
                        }
                        task.setTitle(data.getTitle());
                    }
                    if (data.getTasklistId() != null) {
                        var newTasklist = tasklistRepository.findById(UUID.fromString(data.getTasklistId()))
                                .orElseThrow(() -> new ResourceNotFoundException("Tasklist not found"));
                        if (!newTasklist.getUserId().equals(userId)) {
                            throw new ResourceNotFoundException("Tasklist not found");
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
                    throw new ResourceNotFoundException("Task not found");
                });
    }
}

