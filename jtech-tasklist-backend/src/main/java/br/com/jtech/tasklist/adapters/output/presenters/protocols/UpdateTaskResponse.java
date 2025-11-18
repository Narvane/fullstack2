package br.com.jtech.tasklist.adapters.output.presenters.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateTaskResponse {
    private String id;
    private String title;
    private Boolean completed;
    private String tasklistId;
}

