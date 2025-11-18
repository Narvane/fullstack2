package br.com.jtech.tasklist.adapters.output.presenters.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticateUserResponse {
    private String id;
    private String name;
    private String email;
    private String token;
}

