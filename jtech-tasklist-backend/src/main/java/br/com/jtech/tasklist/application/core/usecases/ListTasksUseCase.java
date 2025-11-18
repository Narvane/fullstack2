package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListTasksUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;

    public ListTasksUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository) {
        this.outputGateway = outputGateway;
        this.taskRepository = taskRepository;
    }

    @Override
    public void exec(TaskInputData data) {
        List<br.com.jtech.tasklist.application.core.domains.Task> tasks;
        
        if (data.getTasklistId() != null) {
            tasks = taskRepository.findByTasklistId(UUID.fromString(data.getTasklistId()));
        } else {
            tasks = taskRepository.findAll();
        }

        var tasksOutput = tasks.stream()
                .map(task -> TaskOutputData.builder()
                        .id(task.getId().toString())
                        .title(task.getTitle())
                        .completed(task.isCompleted())
                        .tasklistId(task.getTasklistId())
                        .build())
                .collect(Collectors.toList());

        outputGateway.exec(
                TaskOutputData.builder()
                        .tasks(tasksOutput)
                        .build()
        );
    }
}

