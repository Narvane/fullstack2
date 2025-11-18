package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.CompleteTaskResponse;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskOutputData;
import lombok.Getter;

@Getter
public class CompleteTaskPresenter implements TaskOutputGateway {

    private CompleteTaskResponse response;

    @Override
    public void exec(TaskOutputData data) {
        response = CompleteTaskResponse.builder()
                .id(data.getId())
                .title(data.getTitle())
                .completed(data.getCompleted())
                .tasklistId(data.getTasklistId())
                .build();
    }
}

