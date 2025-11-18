package br.com.jtech.tasklist.application.ports.protocols;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserOutputData {
    private String id;
    private String name;
    private String email;
    private String token;
}

