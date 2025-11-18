package br.com.jtech.tasklist.adapters.protocols.tasklist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTasklistRequest {
    private String id;
    private String title;
}
