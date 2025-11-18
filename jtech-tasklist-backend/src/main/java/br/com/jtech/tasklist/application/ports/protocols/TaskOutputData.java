package br.com.jtech.tasklist.application.ports.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskOutputData {
    private String id;
    private String title;
    private Boolean completed;
    private String tasklistId;
}

