package br.com.jtech.tasklist.application.core.usecases;


import br.com.jtech.tasklist.application.core.domains.Tasklist;
import br.com.jtech.tasklist.application.ports.input.CreateTasklistInputGateway;
import br.com.jtech.tasklist.application.ports.output.CreateTasklistOutputGateway;

import java.util.UUID;

public class CreateTasklistUseCase implements CreateTasklistInputGateway {

    private final CreateTasklistOutputGateway createTasklistOutputGateway;

    public CreateTasklistUseCase(CreateTasklistOutputGateway createTasklistOutputGateway) {
        this.createTasklistOutputGateway = createTasklistOutputGateway;
     }

    public Tasklist exec(String title) {
        return createTasklistOutputGateway.create(
                Tasklist.builder()
                        .id(UUID.randomUUID())
                        .title(title)
                        .build()
        );
     }

 }