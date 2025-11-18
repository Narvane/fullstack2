package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;

import java.util.UUID;

public class DeleteTaskUseCase implements TaskInputGateway {

    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void exec(TaskInputData data) {
        taskRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(task -> {
                    taskRepository.deleteById(UUID.fromString(data.getId()));
                }, () -> {
                    throw new RuntimeException("Task not found");
                });
    }
}

