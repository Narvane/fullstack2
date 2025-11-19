package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.output.presenters.ListTasksPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.ListTasksResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.ListTasks;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management endpoints")
public class ListTasksController {

    private final TaskInputGateway inputGateway;
    private final ListTasksPresenter presenter;

    public ListTasksController(
            @ListTasks TaskInputGateway inputGateway,
            @ListTasks TaskOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (ListTasksPresenter) outputGateway;
    }

    @Operation(
            summary = "List tasks",
            description = "Returns tasks. If taskListId is provided, returns tasks from that tasklist. Otherwise, returns all tasks from user's tasklists."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tasks retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ListTasksResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tasklist not found (when taskListId is provided)",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "bearerAuth")
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
