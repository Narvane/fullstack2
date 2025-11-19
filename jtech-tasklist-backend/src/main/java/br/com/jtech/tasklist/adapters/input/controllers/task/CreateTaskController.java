package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.protocols.task.CreateTaskRequest;
import br.com.jtech.tasklist.adapters.output.presenters.CreateTaskPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTaskResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.CreateTask;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/tasks")
public class CreateTaskController {

    private final TaskInputGateway inputGateway;
    private final CreateTaskPresenter presenter;

    public CreateTaskController(
            @CreateTask TaskInputGateway inputGateway,
            @CreateTask TaskOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (CreateTaskPresenter) outputGateway;
    }

    @PostMapping
    public ResponseEntity<CreateTaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        inputGateway.exec(
                TaskInputData.builder()
                        .title(request.getTitle())
                        .tasklistId(request.getTasklistId())
                        .build()
        );
        return ResponseEntity.status(CREATED).body(presenter.getResponse());
    }
}
