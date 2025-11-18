package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.output.presenters.CompleteTaskPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CompleteTaskResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.CompleteTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class CompleteTaskController {

    private final TaskInputGateway inputGateway;
    private final CompleteTaskPresenter presenter;

    public CompleteTaskController(
            @CompleteTask TaskInputGateway inputGateway,
            @CompleteTask TaskOutputGateway outputGateway
    ) {
        this.inputGateway = inputGateway;
        this.presenter = (CompleteTaskPresenter) outputGateway;
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<CompleteTaskResponse> complete(@PathVariable String id) {
        inputGateway.exec(
                TaskInputData.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(presenter.getResponse());
    }
}
