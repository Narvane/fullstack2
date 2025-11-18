package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.UpdateTaskResponse;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import lombok.Getter;

public class UpdateTaskPresenter implements TaskOutputGateway {

    @Getter private UpdateTaskResponse response;

    @Override
    public void exec(TaskOutputData data) {
        response = UpdateTaskResponse.builder()
                .id(data.getId())
                .title(data.getTitle())
                .completed(data.getCompleted())
                .tasklistId(data.getTasklistId())
                .build();
    }
}

