package br.com.jtech.tasklist.application.ports.output;

import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;

import java.util.List;

public interface TaskOutputGateway {

    void exec(TaskOutputData data);

    default void execAll(List<TaskOutputData> data) {
        data.forEach(this::exec);
    };

}

