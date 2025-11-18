package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasksResponse;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;
import lombok.Getter;

@Getter
public class ListTasksPresenter implements TaskOutputGateway {

    private ListTasksResponse response;

    @Override
    public void exec(TaskOutputData data) {
        response = ListTasksResponse.builder()
                .tasks(data.getTasks())
                .build();
    }
}

