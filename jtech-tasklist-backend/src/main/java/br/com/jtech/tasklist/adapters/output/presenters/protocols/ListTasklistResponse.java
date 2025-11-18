package br.com.jtech.tasklist.adapters.output.presenters.protocols;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ListTasklistResponse {
    private List<TaskListResponse> tasklists;

    @Builder
    @Getter
    public static class TaskListResponse {
        private String id;
        private String title;
    }
}

