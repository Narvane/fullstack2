package br.com.jtech.tasklist.adapters.output.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.protocols.AuthenticateUserResponse;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.application.ports.protocols.UserOutputData;
import lombok.Getter;

@Getter
public class AuthenticateUserPresenter implements UserOutputGateway {

    private AuthenticateUserResponse response;

    @Override
    public void exec(UserOutputData data) {
        response = AuthenticateUserResponse.builder()
                .id(data.getId())
                .name(data.getName())
                .email(data.getEmail())
                .token(data.getToken())
                .build();
    }

}

