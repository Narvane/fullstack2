package br.com.jtech.tasklist.application.core.usecases;

import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TasklistOutputData;
import br.com.jtech.tasklist.application.ports.output.repositories.TasklistRepository;

import java.util.List;
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
        List<Tasklist> tasklists = tasklistRepository.findAll();

        var tasklistsOutput = tasklists.stream()
                .map(tasklist -> TasklistOutputData.builder()
                        .id(tasklist.getId().toString())
                        .title(tasklist.getTitle())
                        .build())
                .collect(Collectors.toList());

        outputGateway.execAll(tasklistsOutput);
    }
}

