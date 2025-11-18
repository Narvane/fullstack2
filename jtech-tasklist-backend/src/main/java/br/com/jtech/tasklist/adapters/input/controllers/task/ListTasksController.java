package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.output.presenters.ListTasksPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasksResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.ListTasks;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
public class ListTasksController {

    private final TaskInputGateway inputGateway;
    private final ListTasksPresenter presenter;

    public ListTasksController(
            @ListTasks TaskInputGateway inputGateway,
            @ListTasks TaskOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (ListTasksPresenter) outputGateway;
    }

    @GetMapping
    public ResponseEntity<ListTasksResponse> list(
            @RequestParam(name = "taskListId", required = false) String taskListId) {
        inputGateway.exec(
                TaskInputData.builder()
                        .tasklistId(taskListId)
                        .build()
        );
        return ResponseEntity.ok(presenter.getResponse());
    }
}
