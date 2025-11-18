package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.RegisterUserResponse;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserOutputData;
import lombok.Getter;

@Getter
public class RegisterUserPresenter implements UserOutputGateway {

    private RegisterUserResponse response;

    @Override
    public void exec(UserOutputData data) {
        response = RegisterUserResponse.builder()
                .id(data.getId())
                .name(data.getName())
                .email(data.getEmail())
                .token(data.getToken())
                .build();
    }

}

