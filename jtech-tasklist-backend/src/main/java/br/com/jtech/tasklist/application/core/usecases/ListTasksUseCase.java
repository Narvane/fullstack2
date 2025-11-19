package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.exceptions.ResourceNotFoundException;
import br.com.jtech.tasklist.config.infra.exceptions.UnauthorizedException;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListTasksUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;
    private final TasklistRepository tasklistRepository;

    public ListTasksUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository, TasklistRepository tasklistRepository) {
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

        List<br.com.jtech.tasklist.application.core.domains.Task> tasks;
        
        if (data.getTasklistId() != null) {
            var tasklist = tasklistRepository.findById(UUID.fromString(data.getTasklistId()))
                    .orElseThrow(() -> new ResourceNotFoundException("Tasklist not found"));
            if (!tasklist.getUserId().equals(userId)) {
                throw new ResourceNotFoundException("Tasklist not found");
            }
            tasks = taskRepository.findByTasklistId(UUID.fromString(data.getTasklistId()));
        } else {
            var userTasklists = tasklistRepository.findByUserId(userId);
            tasks = userTasklists.stream()
                    .flatMap(tasklist -> taskRepository.findByTasklistId(tasklist.getId()).stream())
                    .collect(Collectors.toList());
        }

        var tasksOutput = tasks.stream()
                .map(task -> TaskOutputData.builder()
                        .id(task.getId().toString())
                        .title(task.getTitle())
                        .completed(task.isCompleted())
                        .tasklistId(task.getTasklistId())
                        .build())
                .collect(Collectors.toList());

        outputGateway.execAll(tasksOutput);
    }
}

