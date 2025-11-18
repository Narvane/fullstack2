package br.com.jtech.tasklist.application.ports.input.data;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TasklistInputData {
    private String id;
    private String title;
}
