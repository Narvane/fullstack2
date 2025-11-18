package br.com.jtech.tasklist.application.ports.output.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TasklistOutputData {
    private String id;
    private String title;
}
