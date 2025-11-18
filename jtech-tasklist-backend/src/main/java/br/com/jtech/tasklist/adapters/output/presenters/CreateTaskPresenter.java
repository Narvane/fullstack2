package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTaskResponse;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;
import lombok.Getter;

@Getter
public class CreateTaskPresenter implements TaskOutputGateway {

    private CreateTaskResponse response;

    @Override
    public void exec(TaskOutputData data) {
        response = CreateTaskResponse.builder()
                .id(data.getId())
                .title(data.getTitle())
                .completed(data.getCompleted())
                .tasklistId(data.getTasklistId())
                .build();
    }
}

