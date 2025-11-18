package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasklistResponse;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ListTasklistPresenter implements TasklistOutputGateway {

    private final List<ListTasklistResponse.TaskListResponse> tasklists = new ArrayList<>();

    @Override
    public void exec(TasklistOutputData data) {
        tasklists.add(
                ListTasklistResponse.TaskListResponse.builder()
                        .id(data.getId())
                        .title(data.getTitle())
                        .build()
        );
    }

    public ListTasklistResponse getResponse() {
        return ListTasklistResponse.builder()
                .tasklists(List.copyOf(tasklists))
                .build();
    }

}