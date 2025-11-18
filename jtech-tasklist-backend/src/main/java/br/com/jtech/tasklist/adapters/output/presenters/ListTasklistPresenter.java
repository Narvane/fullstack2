package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasklistResponse;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TasklistOutputData;
import lombok.Getter;

public class ListTasklistPresenter implements TasklistOutputGateway {

    @Getter private ListTasklistResponse response;

    @Override
    public void exec(TasklistOutputData data) {
        response = ListTasklistResponse.builder()
                .tasklists(data.getTasklists())
                .build();
    }
}

