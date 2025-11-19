package br.com.jtech.tasklist.adapters.input.controllers.task;

import br.com.jtech.tasklist.application.ports.input.TaskInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TaskInputData;
import br.com.jtech.tasklist.config.qualifiers.DeleteTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tasks")
@Tag(name = "Tasks", description = "Task management endpoints")
public class DeleteTaskController {

    private final TaskInputGateway inputGateway;

    public DeleteTaskController(@DeleteTask TaskInputGateway inputGateway) {
        this.inputGateway = inputGateway;
    }

    @Operation(
            summary = "Delete a task",
            description = "Deletes a task. Only the owner of the tasklist can delete tasks."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Task deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Task not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        inputGateway.exec(
                TaskInputData.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.noContent().build();
    }
}
