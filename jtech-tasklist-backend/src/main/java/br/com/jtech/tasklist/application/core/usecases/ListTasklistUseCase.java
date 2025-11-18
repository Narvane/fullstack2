package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;
import br.com.jtech.tasklist.config.infra.security.SecurityContext;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListTasklistUseCase implements TasklistInputGateway {

    private final TasklistOutputGateway outputGateway;
    private final TasklistRepository tasklistRepository;

    public ListTasklistUseCase(TasklistOutputGateway outputGateway, TasklistRepository tasklistRepository) {
        this.outputGateway = outputGateway;
        this.tasklistRepository = tasklistRepository;
    }

    @Override
    public void exec(TasklistInputData data) {
        UUID userId = SecurityContext.getCurrentUserId();
        if (userId == null) {
            throw new RuntimeException("User not authenticated");
        }

        List<Tasklist> tasklists = tasklistRepository.findByUserId(userId);

        var tasklistsOutput = tasklists.stream()
                .map(tasklist -> TasklistOutputData.builder()
                        .id(tasklist.getId().toString())
                        .title(tasklist.getTitle())
                        .build())
                .collect(Collectors.toList());

        outputGateway.execAll(tasklistsOutput);
    }
}

