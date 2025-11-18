package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.input.controllers.protocols.task.UpdateTaskRequest;
import br.com.jtech.tasklist.adapters.output.presenters.UpdateTaskPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.UpdateTaskResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.usecases.qualifiers.UpdateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class UpdateTaskController {

    private final TaskInputGateway inputGateway;
    private final UpdateTaskPresenter presenter;

    public UpdateTaskController(
            @UpdateTask TaskInputGateway inputGateway,
            @UpdateTask TaskOutputGateway outputGateway
    ) {
        this.inputGateway = inputGateway;
        this.presenter = (UpdateTaskPresenter) outputGateway;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTaskResponse> update(
            @PathVariable String id,
            @RequestBody UpdateTaskRequest request) {

        inputGateway.exec(
                TaskInputData.builder()
                        .id(id)
                        .title(request.getTitle())
                        .tasklistId(request.getTasklistId())
                        .build()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(presenter.getResponse());
    }
}
