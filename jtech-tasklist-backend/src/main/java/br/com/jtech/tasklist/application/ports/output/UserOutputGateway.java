package br.com.jtech.tasklist.application.ports.output;

import br.com.jtech.tasklist.application.ports.protocols.UserOutputData;

public interface UserOutputGateway {

    void exec(UserOutputData data);

}

