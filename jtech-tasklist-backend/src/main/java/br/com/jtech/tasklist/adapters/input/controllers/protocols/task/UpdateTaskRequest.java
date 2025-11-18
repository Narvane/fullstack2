package br.com.jtech.tasklist.adapters.input.controllers.protocols.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {
    private String title;
    private String tasklistId;
}

