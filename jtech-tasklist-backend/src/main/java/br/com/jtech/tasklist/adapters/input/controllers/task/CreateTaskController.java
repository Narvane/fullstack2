package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.protocols.task.CreateTaskRequest;
import br.com.jtech.tasklist.adapters.output.presenters.CreateTaskPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CreateTaskResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.CreateTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management endpoints")
public class CreateTaskController {

    private final TaskInputGateway inputGateway;
    private final CreateTaskPresenter presenter;

    public CreateTaskController(
            @CreateTask TaskInputGateway inputGateway,
            @CreateTask TaskOutputGateway outputGateway) {
        this.inputGateway = inputGateway;
        this.presenter = (CreateTaskPresenter) outputGateway;
    }

    @Operation(
            summary = "Create a new task",
            description = "Creates a new task in the specified tasklist. Duplicate titles are not allowed within the same tasklist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = CreateTaskResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tasklist not found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict - A task with this title already exists in this tasklist",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "bearerAuth")
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
