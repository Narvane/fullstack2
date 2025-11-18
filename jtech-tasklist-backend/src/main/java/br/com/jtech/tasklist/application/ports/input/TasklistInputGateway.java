package br.com.jtech.tasklist.application.ports.input;

import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;

public interface TasklistInputGateway {

    void exec(TasklistInputData data);

}
