package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.output.presenters.ListTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.ListTasklist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasklists")
public class ListTasklistController {

    private final TasklistInputGateway inputGateway;
    private final ListTasklistPresenter presenter;

    public ListTasklistController(
            @ListTasklist TasklistInputGateway inputGateway,
            @ListTasklist TasklistOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (ListTasklistPresenter) outputGateway;
    }

    @GetMapping
    public ResponseEntity<ListTasklistResponse> listTasklists() {
        inputGateway.exec(
                TasklistInputData.builder()
                        .build()
        );
        return ResponseEntity.ok(presenter.getResponse());
    }
}
