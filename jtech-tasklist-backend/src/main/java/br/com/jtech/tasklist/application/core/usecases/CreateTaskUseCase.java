package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.Task;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TaskRepository;

public class CreateTaskUseCase implements TaskInputGateway {

    private final TaskOutputGateway outputGateway;
    private final TaskRepository taskRepository;

    public CreateTaskUseCase(TaskOutputGateway outputGateway, TaskRepository taskRepository) {
        this.outputGateway = outputGateway;
        this.taskRepository = taskRepository;
    }

    @Override
    public void exec(TaskInputData data) {
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

