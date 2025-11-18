package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTasklistResponse;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;
import lombok.Getter;

@Getter
public class CreateTasklistPresenter implements TasklistOutputGateway {

    private CreateTasklistResponse response;

    @Override
    public void exec(TasklistOutputData data) {
        response = CreateTasklistResponse.builder()
                .id(data.getId())
                .title(data.getTitle())
                .build();
    }

}
