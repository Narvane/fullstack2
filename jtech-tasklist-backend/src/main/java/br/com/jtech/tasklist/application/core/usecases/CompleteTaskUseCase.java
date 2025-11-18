package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;

import java.util.UUID;

public class CompleteTaskUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;

    public CompleteTaskUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository) {
        this.outputGateway = outputGateway;
        this.taskRepository = taskRepository;
    }

    @Override
    public void exec(TaskInputData data) {
        taskRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(task -> {
                    task.setCompleted(!task.isCompleted());

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

