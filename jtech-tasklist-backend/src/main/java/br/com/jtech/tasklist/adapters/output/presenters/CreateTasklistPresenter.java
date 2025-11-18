package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTasklistResponse;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TasklistOutputData;
import lombok.Getter;

public class CreateTasklistPresenter implements TasklistOutputGateway {

    @Getter private CreateTasklistResponse response;

    @Override
    public void exec(TasklistOutputData data) {
        response = CreateTasklistResponse.builder()
                .id(data.getId())
                .title(data.getTitle())
                .build();
    }

}
