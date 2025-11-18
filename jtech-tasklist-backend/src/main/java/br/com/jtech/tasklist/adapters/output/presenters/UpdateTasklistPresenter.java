package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.UpdateTasklistResponse;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.application.ports.output.data.TasklistOutputData;
import lombok.Getter;


public class UpdateTasklistPresenter implements TasklistOutputGateway {

    @Getter private UpdateTasklistResponse response;

    @Override
    public void exec(TasklistOutputData data) {

    }

}
