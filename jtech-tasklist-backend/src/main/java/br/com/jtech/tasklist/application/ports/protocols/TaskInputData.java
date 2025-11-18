package br.com.jtech.tasklist.application.ports.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TaskInputData {
    private String id;
    private String title;
    private String tasklistId;
    private Boolean completed;
}

