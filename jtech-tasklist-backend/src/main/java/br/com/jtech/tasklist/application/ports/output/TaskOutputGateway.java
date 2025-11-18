package br.com.jtech.tasklist.application.ports.output;

import br.com.jtech.tasklist.application.ports.output.data.TaskOutputData;

public interface TaskOutputGateway {

    void exec(TaskOutputData data);

}

