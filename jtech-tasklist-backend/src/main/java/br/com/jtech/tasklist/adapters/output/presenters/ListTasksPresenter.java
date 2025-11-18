package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasksResponse;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListTasksPresenter implements TaskOutputGateway {

    private final List<ListTasksResponse.TaskResponse> tasks = new ArrayList<>();

    @Override
    public void exec(TaskOutputData data) {
        tasks.add(
                ListTasksResponse.TaskResponse.builder()
                        .id(data.getId())
                        .title(data.getTitle())
                        .completed(data.getCompleted())
                        .tasklistId(data.getTasklistId())
                        .build()
        );
    }

    public ListTasksResponse getResponse() {
        return ListTasksResponse.builder()
                .tasks(List.copyOf(tasks))
                .build();
    }

}
