package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TasklistOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;

import java.util.UUID;

public class UpdateTasklistUseCase implements TasklistInputGateway {

    private final TasklistOutputGateway outputGateway;
    private final TasklistRepository tasklistRepository;

    public UpdateTasklistUseCase(TasklistOutputGateway outputGateway,
                                 TasklistRepository tasklistRepository) {
        this.outputGateway = outputGateway;
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TasklistInputData data) {
        tasklistRepository.findById(UUID.fromString(data.getId()))
                .ifPresentOrElse(tasklist -> {
                    tasklist.setTitle(data.getTitle());

                    var updatedTask = tasklistRepository.save(tasklist);

                    outputGateway.exec(
                            TasklistOutputData.builder()
                                    .id(updatedTask.getId().toString())
                                    .title(updatedTask.getTitle())
                            .build()
                    );
                }, () -> {
                    throw new RuntimeException("Tasklist not found");
                });
    }

}
