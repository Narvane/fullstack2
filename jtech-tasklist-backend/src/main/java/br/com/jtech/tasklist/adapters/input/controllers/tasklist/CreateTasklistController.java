package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.protocols.tasklist.CreateTasklistRequest;
import br.com.jtech.tasklist.adapters.output.presenters.CreateTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.CreateTasklist;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/tasklists")
public class CreateTasklistController {

    private final TasklistInputGateway inputGateway;
    private final CreateTasklistPresenter presenter;

    public CreateTasklistController(
            @CreateTasklist TasklistInputGateway inputGateway,
            @CreateTasklist TasklistOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (CreateTasklistPresenter) outputGateway;
    }

    @PostMapping
    public ResponseEntity<CreateTasklistResponse> create(@Valid @RequestBody CreateTasklistRequest request) {
        inputGateway.exec(
                TasklistInputData.builder()
                        .title(request.getTitle())
                        .build()
        );
        return ResponseEntity.status(CREATED).body(presenter.getResponse());
    }

}