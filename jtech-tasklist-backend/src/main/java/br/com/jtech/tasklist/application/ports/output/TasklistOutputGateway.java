package br.com.jtech.tasklist.application.ports.output;

import br.com.jtech.tasklist.application.ports.protocols.TasklistOutputData;

import java.util.List;

public interface TasklistOutputGateway {

    void exec(TasklistOutputData data);

    default void execAll(List<TasklistOutputData> data) {
        data.forEach(this::exec);
    };

}
