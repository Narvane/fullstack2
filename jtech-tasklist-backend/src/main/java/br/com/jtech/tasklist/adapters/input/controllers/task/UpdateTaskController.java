package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.protocols.task.UpdateTaskRequest;
import br.com.jtech.tasklist.adapters.output.presenters.UpdateTaskPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.UpdateTaskResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.UpdateTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management endpoints")
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

    @Operation(
            summary = "Update a task",
            description = "Updates task properties (title, tasklist, completion status). Duplicate titles are not allowed within the same tasklist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = UpdateTaskResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task or tasklist not found",
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
    @PutMapping("/{id}")
    public ResponseEntity<UpdateTaskResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateTaskRequest request) {

        inputGateway.exec(
                TaskInputData.builder()
                        .id(id)
                        .title(request.getTitle())
                        .tasklistId(request.getTasklistId())
                        .completed(request.getCompleted())
                        .build()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(presenter.getResponse());
    }
}
