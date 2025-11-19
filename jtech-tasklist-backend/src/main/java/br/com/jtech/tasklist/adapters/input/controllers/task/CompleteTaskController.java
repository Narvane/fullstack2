package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.adapters.output.presenters.CompleteTaskPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.CompleteTaskResponse;
import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.application.ports.output.TaskOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.CompleteTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management endpoints")
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

    @Operation(
            summary = "Toggle task completion status",
            description = "Toggles the completion status of a task (completed <-> not completed)"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Task completion status toggled successfully",
                    content = @Content(schema = @Schema(implementation = CompleteTaskResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found",
                    content = @Content
            )
    })
    @SecurityRequirement(name = "bearerAuth")
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
