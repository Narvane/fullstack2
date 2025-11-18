package br.com.jtech.tasklist.adapters.protocols.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {
    private String title;
    private String tasklistId;
}

