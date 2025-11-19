package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.config.qualifiers.DeleteTaskList;
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
@RequestMapping("/api/v1/tasklists")
@Tag(name = "Tasklists", description = "Tasklist management endpoints")
public class DeleteTasklistController {

    private final TasklistInputGateway inputGateway;

    public DeleteTasklistController(@DeleteTaskList TasklistInputGateway inputGateway) {
        this.inputGateway = inputGateway;
    }

    @Operation(
            summary = "Delete a tasklist",
            description = "Deletes a tasklist and all its tasks. Only the owner can delete their tasklist."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Tasklist deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - User not authenticated"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tasklist not found"
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        inputGateway.exec(
                TasklistInputData.builder()
                        .id(id)
                        .build()
        );
        return ResponseEntity.noContent().build();
    }
}
