package br.com.jtech.tasklist.application.ports.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserInputData {
    private String name;
    private String email;
    private String password;
}

