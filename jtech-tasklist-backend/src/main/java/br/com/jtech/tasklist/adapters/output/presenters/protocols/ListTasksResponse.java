package br.com.jtech.tasklist.adapters.output.presenters.protocols;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ListTasksResponse {
    private List<TaskResponse> tasks;

    @Builder
    @Getter
    public static class TaskResponse {
        private String id;
        private String title;
        private Boolean completed;
        private String tasklistId;
    }

}

