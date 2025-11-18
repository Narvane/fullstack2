package br.com.jtech.tasklist.application.ports.input;

import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;

public interface TaskInputGateway {

    void exec(TaskInputData data);

}

