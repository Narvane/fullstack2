package br.com.jtech.tasklist.adapters.input.controllers.tasklist;

import br.com.jtech.tasklist.adapters.protocols.tasklist.UpdateTasklistRequest;
import br.com.jtech.tasklist.adapters.output.presenters.UpdateTasklistPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.protocols.UpdateTasklistResponse;
import br.com.jtech.tasklist.application.ports.input.TasklistInputGateway;
import br.com.jtech.tasklist.application.ports.protocols.TasklistInputData;
import br.com.jtech.tasklist.application.ports.output.TasklistOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.UpdateTasklist;
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
@RequestMapping("/api/v1/tasklists")
@Tag(name = "Tasklists", description = "Tasklist management endpoints")
public class UpdateTasklistController {

    private final TasklistInputGateway inputGateway;
    private final UpdateTasklistPresenter presenter;

    public UpdateTasklistController(
            @UpdateTasklist TasklistInputGateway inputGateway,
            @UpdateTasklist TasklistOutputGateway outputGateway
    ) {
        this.inputGateway = inputGateway;
        this.presenter = (UpdateTasklistPresenter) outputGateway;
    }

    @Operation(
            summary = "Update a tasklist",
            description = "Updates the title of a tasklist. Duplicate titles are not allowed per user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tasklist updated successfully",
                    content = @Content(schema = @Schema(implementation = UpdateTasklistResponse.class))
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
                    description = "Conflict - A tasklist with this title already exists",
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
    public ResponseEntity<UpdateTasklistResponse> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateTasklistRequest request) {

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
