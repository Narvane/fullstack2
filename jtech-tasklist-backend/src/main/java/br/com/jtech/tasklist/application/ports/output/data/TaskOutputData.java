package br.com.jtech.tasklist.application.ports.output.data;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class TaskOutputData {
    private String id;
    private String title;
    private Boolean completed;
    private String tasklistId;
}

