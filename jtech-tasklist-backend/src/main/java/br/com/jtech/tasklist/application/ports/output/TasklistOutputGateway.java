package br.com.jtech.tasklist.application.ports.output;

import br.com.jtech.tasklist.application.ports.output.data.TasklistOutputData;

public interface TasklistOutputGateway {

    void exec(TasklistOutputData data);

}
