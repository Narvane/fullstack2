package br.com.jtech.tasklist.application.ports.input;

import br.com.jtech.tasklist.application.ports.protocols.UserInputData;

public interface UserInputGateway {

    void exec(UserInputData data);

}

