package br.com.jtech.tasklist.adapters.output.presenters.protocols;

import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ListTasksResponse {
    private List<TaskOutputData> tasks;
}

