package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.input.controllers.protocols.tasklist.UpdateTasklistRequest;
import br.com.jtech.tasklist.adapters.output.presenters.UpdateTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.UpdateTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.input.data.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.config.usecases.qualifiers.UpdateTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasklists")
public class UpdateTasklistController {

    private final TasklistInputGateway inputGateway;
    private final UpdateTasklistPresenter presenter;

    public UpdateTasklistController(
            @UpdateTask TasklistInputGateway inputGateway,
            @UpdateTask TasklistOutputGateway outputGateway
    ) {
        this.inputGateway = inputGateway;
        this.presenter = (UpdateTasklistPresenter) outputGateway;
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateTasklistResponse> update(
            @PathVariable String id,
            @RequestBody UpdateTasklistRequest request) {

        inputGateway.exec(
                TasklistInputData.builder()
                        .id(id)
                        .title(request.getTitle())
                        .build()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(presenter.getResponse());
    }

}
