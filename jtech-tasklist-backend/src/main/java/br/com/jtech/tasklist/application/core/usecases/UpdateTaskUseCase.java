package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;

import java.util.UUID;

public class UpdateTaskUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;

    public UpdateTaskUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository) {
        this.outputGateway = outputGateway;
        this.taskRepository = taskRepository;
    }

    @Override
    public void exec(TaskInputData data) {
        taskRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(task -> {
                    if (data.getTitle() != null) {
                        task.setTitle(data.getTitle());
                    }
                    if (data.getTasklistId() != null) {
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

